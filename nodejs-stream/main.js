const express = require('express');
const cors = require('cors');
const {pipeline, Transform} = require('node:stream');
const mongoose = require('mongoose');
const fastcsv = require('fast-csv');
const app = express();
const PORT = process.env.PORT || 9090;
app.use(cors({
    origin: '*',
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
    allowedHeaders: ['Content-Type', 'Authorization'],
    credentials: false
}));

// Connection à MongoDB
mongoose.connect('mongodb://mongodb:27017/test', {
    useNewUrlParser: true,
    useUnifiedTopology: true
});

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function () {
    console.log('Connected to MongoDB');
});

const ItemSchema = new mongoose.Schema({
    brand: String,
    sellerRef: String,
    quantity: Number,
    unitPrice: Number
});

const Item = mongoose.model('Item', ItemSchema, 'inventory');

function createDelayStream(delayTime) {
    return new Transform({
        objectMode: true,
        transform(item, encoding, callback) {
            setTimeout(() => {
                callback(null, item.toString());
            }, delayTime);
        }
    });
}


app.get('/items', (req, res) => {
    try {
        console.log('Connected je suis la dernière version avec opti du stream');
        const delayTimeInMillis = parseInt(req.query.delay) || 0; // Récupère le délai de la requête, 0 par défaut
        const itemStream = Item.find().cursor();
        const delayStream = createDelayStream(delayTimeInMillis);
        console.log('delay stream created !');

        res.setHeader('Content-Type', 'text/csv');
        res.setHeader('Content-Disposition', 'attachment; filename=items.csv');
        res.setHeader('Cache-Control', 'no-cache');
        res.setHeader('Pragma', 'no-cache');

        const csvStream = fastcsv.format({
            headers: ['brand', 'sellerRef', 'quantity', 'unitPrice'],
            writeHeaders: true,
            delimiter: ';'
        });

        console.log('csv stream created !');

        pipeline(itemStream,
            csvStream,
            delayStream,
            res,
            (err) => {
                if (err) {
                    console.error(err);
                    res.status(500).send(err)
                }
            });

        console.log('operation pipe done !');

    } catch (error) {
        console.error(error);
        res.status(500).send(error);
    }
});
app.get('/test-mongo', async (req, res) => {
    try {
        const items = await Item.find().limit(10).exec();
        res.setHeader('Cache-Control', 'no-cache, no-store, must-revalidate');
        res.send(items);
    } catch (error) {
        console.error(error);
        res.status(500).send(error);
    }
});

app.get('/test-count', async (req, res) => {
    try {
        const count = await Item.countDocuments();
        res.send(`Number of documents: ${count}`);
    } catch (error) {
        console.error(error);
        res.status(500).send(error);
    }
});


app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}/items`);
});
