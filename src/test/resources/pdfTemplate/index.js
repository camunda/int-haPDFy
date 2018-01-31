'use strict';

var fs = require("fs");
var http = require('http');
var request = require('request');
var outputPath = 'ouptut.pdf';

$(function() {
    const formData = {
        file: {
            value:  fs.createReadStream('input.html'),
            options: {
                filename: 'input.html',
                contentType: 'text/html; charset=utf-8'
            }
        }

    };

    createPdf();

    function createPdf() {
        let fstream = fs.createWriteStream(outputPath);
        var payload = {};

    $.ajax({
        type: 'POST',
        url:  'http://192.168.99.100:8080/convert?auth=arachnys-weaver&ext=html',
        data: JSON.stringify(payload),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
      });
        /*
        request.post({
            url:'http://192.168.99.100:8080/convert?auth=arachnys-weaver&ext=html',
            headers: {
                authorization: 'arachnys-weaver',
            },
            formData: formData

        })
        .on('complete', function (){
            console.log('Request finished');
        })
        .on('error', function(err){
        })
        .pipe(fstream);*/
    }
}