import { HomeComponent } from './home.component';
import {fireEvent, render, RenderResult, screen} from "@testing-library/angular";
import {MatIconModule} from "@angular/material/icon";
import {Directive, HostListener, Input} from "@angular/core";

describe('HomeComponent', () => {

  @Directive({
    selector: '[routerLink]'
  })
  class RouterLinkDirectiveStub {
    @Input('routerLink') linkParams: any;
    @HostListener('click')
    onClick() {
      navigateByUrlMock(this.linkParams);
    }
  }

  let renderResult:RenderResult<HomeComponent>;
  let navigateByUrlMock = jest.fn().mockReturnValue(true);
  beforeEach(async () => {
    renderResult = await render(HomeComponent,{
      declarations : [
        RouterLinkDirectiveStub
      ],
      imports : [
        MatIconModule,
      ]
    })
  });

  it('should have only one button with a play arrow', () => {
    expect(screen.getByRole('button')).toBeVisible();
    expect(screen.getByRole('button')).toHaveTextContent('play_arrow');
  });

  it('should navigate to /lobby on button click', async () => {
    fireEvent.click(screen.getByRole('button'));
    expect(navigateByUrlMock).toHaveBeenCalledWith('/lobby');
  });
});
