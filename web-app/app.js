'use strict';
const http = require('http');
var assert = require('assert');
const express= require('express');
const app = express();
// const mustache = require('mustache');
// var pug = require ('pug');
const filesystem = require('fs');
const url = require('url');
const port = Number(process.argv[2]);

app.set('views', './views')
app.set('view engine', 'pug')
app.use(express.static(__dirname + '/public'));

const hbase = require('hbase')
var hclient = hbase({ host: process.argv[3], port: Number(process.argv[4])})

function rowToMap(row) {
    var stats = {}
    row.forEach(function (item) {
        stats[item['column']] = item['$']
    });
    return stats;
}

app.get('/', function (req, res) {
    let facilities;
    hclient.table('christiannenic_facilities_list').scan({ maxVersions: 1 }, (err, rows) => {
        // const facilities = rowToMap(rows)
        // console.info(rowToMap(rows))
        // console.log(rows)
        // console.log(rows.federalprovidernumber);

        // return rowToMap(rows);
        // facilities = rows
        res.render('facilitiesSearch', {facilities : rows})
    })
    // hclient.table('christiannenic_state_list').scan({ maxVersions:1 }, (err, rows => {
    //     res.render('index', {states : rows})
    // }))
    // res.render('index', {facilities : facilities})

})

app.get('/facilities_info/', function (req, res) {
    // console.log(req.query)
    const facility = req.query['facility']
    console.log(facility)
    hclient.table('christiannenic_facilities_list').row(facility).get(function (err, cells){
        // console.info(rowToMap(cells))
        const facilityFpn = rowToMap(cells);
        const fpn = facilityFpn['facility:federalprovidernumber']
        console.log(fpn)
        // console.log(typeof fpn)
        // console.log(cells)
        hclient.table('christiannenic_facility_covid_info').row(fpn).get(function (err, cells){
            console.log(cells)
            const columnFamily = "info:"
            const response = rowToMap(cells)
            console.log(response)
            // const facilities =

            function toPercent(num, total) {
                if (total === 0) {
                    return 0
                } else {
                    return (num / total).toFixed(1)
                }
            }
            const numAllBeds = parseInt(response[columnFamily + "numberAllBeds"])
            // console.log(numAllBeds)
            const occupiedBeds = parseInt(response[columnFamily + "totalnumberoccupiedbeds"])
            // const percentOccupied = (occupiedBeds / numAllBeds).toFixed(1)
            const percentOccupied = toPercent(occupiedBeds, numAllBeds)

            const numVentilators = parseInt(response[columnFamily + "numberventilatorsinfacility"])
            const numVentilatorsInUse = parseInt(response[columnFamily + "numberventilatorsinusecovid"])

            let percentVentilators = -1
            if (numVentilators >= 0) {
                percentVentilators = toPercent(numVentilatorsInUse, numVentilators)
            }



            res.render('facilities_info',{
                providerName : response[columnFamily + "providername"],
                providerAddress: response[columnFamily + "provideraddress"],
                providerCity: response[columnFamily + "providercity"],
                providerState: response[columnFamily + "providerstate"],
                providerZipCode : response[columnFamily + "providerzipcode"],
                totalResidentCases : parseInt(response[columnFamily + "totalresidentcovidadmissions"]),
                totalResidentDeaths : parseInt(response[columnFamily + "totalresidentcoviddeaths"]),
                totalStaffCases : parseInt(response[columnFamily + "totalstaffconfirmedcovid"]),
                totalStaffDeaths : parseInt(response[columnFamily + "totalstaffcoviddeaths"]),
                ableTestResidentsWithinWeek : response[columnFamily + "abletestallresidentswithin7days"],
                avgTimeReceiveResidentTestResults : response[columnFamily + "avgtimetoreceiveresidenttestresults"],
                notTestingLabAccess : response[columnFamily + "nottestingresidentslackaccesslab"],
                notTestingLackTrainedPersonnel : response[columnFamily + "nottestingresidentslackaccesstrainedpersonnel"],
                notTestingLackPPE : response[columnFamily + "nottestingresidentslackofppe"],
                notTestingLackSupplies : response[columnFamily + "nottestingresidentslackofsupplies"],
                notTestingOther : response[columnFamily + "nottestingresidentsother"],
                notTestingUncertaintyReimbursement : response[columnFamily + "nottestingresidentsuncertaintyreimbursement"],
                // numberAllBeds : response[columnFamily + "numberallbeds"]
                percentOccupied : percentOccupied,
                // numberVentilators : response[columnFamily + "numberventilatorsinfacility"],
                // numberVentilatorsInUse : response[columnFamily + "numberventilatorsinusecovid"]
                percentVentilators : percentVentilators,
                oneWeekSupplyEyeProtection : response[columnFamily + "oneweeksupplyeyeprotection"],
                oneWeekSupplyGloves : response[columnFamily + "oneweeksupplygloves"],
                oneWeekSupplyGowns : response[columnFamily + "oneweeksupplygowns"],
                oneWeekSupplyHandSanitizer : response[columnFamily + "oneweeksupplyhandsanitizer"],
                oneWeekSupplyN95Masks : response[columnFamily + "oneweeksupplyn95masks"],
                oneWeekSupplySurgicalMasks : response[columnFamily + "oneweeksupplysurgicalmasks"],
                severeDeficiencies : parseInt(response[columnFamily + "severedeficiencies"]),
                severeInfectionDeficiences : parseInt(response[columnFamily + "severeinfectiondeficiencies"]),
                shortageAides : response[columnFamily + "shortageaides"],
                shortageClinicalStaff : response[columnFamily + "shortageclinicalstaff"]


            })
        })
        // res.render('facilities_info', {
        //     fpn : facility
        // })
    })
})
//
// hclient.table('weather_delays_by_route').row('ORDAUS').get((error, value) => {
//     console.info(rowToMap(value))
//     console.info(value)
// })
//
// hclient.table('spertus_carriers').scan({ maxVersions: 1}, (err,rows) => {
//     console.info(rows)
// })
//
// hclient.table('christiannenic_hw71_ontime_hive_hbase').scan({
//         filter: {type : "PrefixFilter",
//             value: "AA"},
//         maxVersions: 1},
//     (err, value) => {
//         console.info(value)
//     })
//
//
// app.use(express.static('public'));
// app.get('/delays.html',function (req, res) {
//     const route=req.query['origin'] + req.query['dest'];
//     console.log(route);
//     hclient.table('weather_delays_by_route').row(route).get(function (err, cells) {
//         const weatherInfo = rowToMap(cells);
//         console.log(weatherInfo)
//         function weather_delay(weather) {
//             var flights = weatherInfo["delay:" + weather + "_flights"];
//             var delays = weatherInfo["delay:" + weather + "_delays"];
//             if(flights == 0)
//                 return " - ";
//             return (delays/flights).toFixed(1); /* One decimal place */
//         }
//
//         var template = filesystem.readFileSync("result.mustache").toString();
//         var html = mustache.render(template,  {
//             origin : req.query['origin'],
//             dest : req.query['dest'],
//             clear_dly : weather_delay("clear"),
//             fog_dly : weather_delay("fog"),
//             rain_dly : weather_delay("rain"),
//             snow_dly : weather_delay("snow"),
//             hail_dly : weather_delay("hail"),
//             thunder_dly : weather_delay("thunder"),
//             tornado_dly : weather_delay("tornado")
//         });
//         res.send(html);
//     });
// });
//
// app.get('/airline-ontime.html', function (req, res) {
//     hclient.table('christiannenic_hw71_carriers_hive_hbase').scan({ maxVersions: 1}, (err,rows) => {
//         var template = filesystem.readFileSync("airline-ontime.mustache").toString();
//         var html = mustache.render(template, {
//             airlines : rows
//         });
//         res.send(html)
//     })
// });
//
// function removePrefix(text, prefix) {
//     console.log("text", text);
//     console.log("prefix", prefix)
//     if(text.indexOf(prefix) != 0) {
//         throw "missing prefix"
//     }
//     return text.substr(prefix.length)
// }
//
// app.get('/airline-ontime-delays.html',function (req, res) {
//     const airline=req.query['airline'];
//     console.log(airline);
//     function processYearRecord(yearRecord) {
//         var result = { year : yearRecord['year']};
//         ["all", "clear", "fog", "hail", "rain", "snow", "thunder", "tornado"].forEach(weather => {
//             var flights = yearRecord[weather + '_flights']
//             var ontime_flights = yearRecord[weather + "_ontimes"]
//             result[weather] = flights == 0 ? "-" : (100 * ontime_flights/flights).toFixed(1)+'%';
//         })
//         return result;
//     }
//     function airlineInfo(cells) {
//         var result = [];
//         var yearRecord;
//         cells.forEach(function(cell) {
//             var year = Number(removePrefix(cell['key'], airline))
//             if(yearRecord === undefined)  {
//                 yearRecord = { year: year }
//             } else if (yearRecord['year'] != year ) {
//                 result.push(processYearRecord(yearRecord))
//                 yearRecord = { year: year }
//             }
//             yearRecord[removePrefix(cell['column'],'ontime:')] = Number(cell['$'])
//         })
//         result.push(processYearRecord(yearRecord))
//         console.info(result)
//         return result;
//     }
//
//     hclient.table('christiannenic_hw71_ontime_hive_hbase').scan({
//             filter: {type : "PrefixFilter",
//                 value: airline},
//             maxVersions: 1},
//         (err, cells) => {
//             var ai = airlineInfo(cells);
//             var template = filesystem.readFileSync("ontime-result.mustache").toString();
//             var html = mustache.render(template, {
//                 airlineInfo : ai,
//                 airline : airline
//             });
//             res.send(html)
//
//         })
// });

app.listen(port);
