import { browser, by, element } from 'protractor';

export class AngularPage {
  navigateTo() {
    browser.ignoreSynchronization = true;
    browser.get('/');

    browser.getCurrentUrl().then(url => {
      if (url.includes('/auth/realms/endeavour/protocol/openid-connect')) {
        element(by.name('username')).sendKeys('e2etest');
        element(by.name('password')).sendKeys('e2eTestPass');
        element(by.name('login')).click();
      }
      browser.ignoreSynchronization = false;

    });
  }

  getParagraphText() {
    return element(by.css('app-root div topnav header div div div span')).getText();
  }
}
