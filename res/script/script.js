https://boletin.gana.com.co/

var cookies = document.evaluate("//button[normalize-space(text())='Aceptar']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE).singleNodeValue;
cookies.click();

var close = document.evaluate("//button[normalize-space(text())='×']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE).singleNodeValue;
close.click();

var body = document.getElementsByTagName('body')[0];
date.click();

var date = document.evaluate("//input[@name='date']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE).singleNodeValue;
date.click();
date['Text'] = '2023-10-25';

var filter = document.evaluate("//button[@class='filter-button']", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE).singleNodeValue;
filter.click();

var lotteries = document.evaluate("//*[contains(@class,'result')]", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE);
