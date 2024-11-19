import {Component} from '@angular/core';
import {DataService} from "../services/data.service";
import {forkJoin, tap} from "rxjs";
import {MatTableDataSource} from "@angular/material/table";

interface Result {
  delay: number;
  akkaStream: number;
  rxJS: number;
  springReactor: number;
  classicDownload: number;
}


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css', '../app.component.css']
})
export class HomeComponent {
  options = [
    { name: 'NodeJS', url: 'http://your-deployment-here/items',timer:0},
    { name: 'Akka Stream', url: 'http://your-deployment-here/items',timer:0},
    { name: 'Reactor', url: 'http://your-deployment-here/items',timer:0},
    { name: 'Classic', url: 'http://your-deployment-here/items',timer:0}
  ];


  selectedOptions:{ [key: string]: boolean } = {
    NodeJS: false,
    'Akka Stream': false,
    Reactor: false,
    Classic: false
  };

  delay = 10;
  results = new MatTableDataSource<Result>();
  displayedColumns: string[] = ['delay', 'akkaStream', 'nodeJS', 'springReactor','classicDownload'];

  constructor(private dataService: DataService) {}

  go() {
    this.options.forEach(o => o.timer=0);
    const selectedUrls = this.options
        .filter(option => this.selectedOptions[option.name])
        .map(option => {
          const startTime = Date.now();
          return this.dataService.fetchData(`${option.url}?delay=${this.delay}`).pipe(
              tap(response => {
                const endTime = Date.now();
                option.timer = (endTime - startTime) / 1000;
              })
          );
        });

    forkJoin(selectedUrls).subscribe(results => {
      const resultEntry: Result = {
        delay: this.delay,
        akkaStream: this.options.find(o => o.name === 'Akka Stream')?.timer || 0,
        rxJS: this.options.find(o => o.name === 'NodeJS')?.timer || 0,
        springReactor: this.options.find(o => o.name === 'Reactor')?.timer || 0,
        classicDownload: this.options.find(o => o.name === 'Classic')?.timer || 0

      };
      const currentData = this.results.data;
      currentData.push(resultEntry);
      this.results.data = currentData;

    });
  }
  increment() {
    this.delay += 2;
  }

  decrement() {
    if (this.delay > 0) {
      this.delay -= 2;
    }
  }

  exportCSV() {
    let dataToExport = this.results.data;
    let csvData = this.convertToCSV(dataToExport);
    let blob = new Blob([csvData], { type: 'text/csv' });
    let url = window.URL.createObjectURL(blob);

    let a = document.createElement('a');
    a.setAttribute('style', 'display:none;');
    a.href = url;
    a.download = 'resultats.csv';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  }

  convertToCSV(objArray: Result[]): string {
    const array = typeof objArray !== 'object' ? JSON.parse(objArray) : objArray;
    let str = '';
    let row = 'Delay, Akka Stream, RxJS, Spring Reactor, Spring Classic\n';

    str += row;

    for (let i = 0; i < array.length; i++) {
      let line = '';
      for (let index in array[i]) {
        if (line !== '') line += ',';
        line += array[i][index];
      }
      str += line + '\r\n';
    }
    return str;
  }

  dlInTab() {
    let delayIncrement = 0;
    const delayAmount = 150;

    this.options
        .filter(option => this.selectedOptions[option.name])
        .forEach(option => {
          setTimeout(() => {
            const filename = `${option.name.replace(/\s/g, '_')}_result.csv`;
            this.triggerDownload(option.url, filename);
          }, delayIncrement);

          delayIncrement += delayAmount;
        });
  }

  triggerDownload(url: string, filename: string) {
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  }
}


