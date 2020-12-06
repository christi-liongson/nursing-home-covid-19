'use strict';
const http = require('http');
var assert = require('assert');
const express= require('express');
const app = express();
const filesystem = require('fs');
const url = require('url');
const port = Number(process.argv[2]);
const path = require ('path')
const fetch = require("node-fetch");
var kafka = require('kafka-node');
var Producer = kafka.Producer;
var KeyedMessage = kafka.KeyedMessage;
var kafkaClient = new kafka.KafkaClient({kafkaHost: process.argv[5]});
var kafkaProducer = new Producer(kafkaClient);

app.set('views', path.join(__dirname, 'views'))
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

function counterToNumber(c) {
    if (c !== undefined) {
        return Number(Buffer.from(c).readBigInt64BE());
    }
    return undefined

}

function removePrefix(text, prefix) {
    console.log("text", text);
    console.log("prefix", prefix)
    if(text.indexOf(prefix) != 0) {
        throw "missing prefix"
    }
    return text.substr(prefix.length)
}

app.get('/', function (req, res) {
    hclient.table('christiannenic_facilities_list').scan({ maxVersions: 1 }, (err, fRows) => {

        hclient.table('christiannenic_state_list').scan( { maxVersions : 1 }, (err, sRows) => {
            console.log(sRows)
            res.render('index', {facilities : fRows, states : sRows})
        })
    })

})

app.get('/facilities_info/', function (req, res) {
    const facility = req.query['facility']
    console.log(facility)
    hclient.table('christiannenic_facilities_list').row(facility).get(function (err, cells){
        const facilityFpn = rowToMap(cells);
        const fpn = facilityFpn['facility:federalprovidernumber']
        console.log("fpn", fpn)
        hclient.table('christiannenic_facility_covid_info').row(fpn).get(function (err, cells){
            console.log(cells)
            const columnFamily = "info:"
            const response = rowToMap(cells)
            console.log(response)

            function toPercent(num, total) {
                if (total === 0) {
                    return 0
                } else {
                    return ((num / total) * 100).toFixed(2)
                }
            }
            const numAllBeds = counterToNumber(response[columnFamily + "numberallbeds"])
            const occupiedBeds = counterToNumber(response[columnFamily + "totalnumberoccupiedbeds"])
            console.log("numallbeds " +  numAllBeds)
            console.log("occupied " +  occupiedBeds)
            const percentOccupied = toPercent(occupiedBeds, numAllBeds)

            const numVentilators = parseInt(response[columnFamily + "numberventilatorsinfacility"])
            const numVentilatorsInUse = parseInt(response[columnFamily + "numberventilatorsinusecovid"])

            let percentVentilators = -1
            if (numVentilators >= 0) {
                percentVentilators = toPercent(numVentilatorsInUse, numVentilators)
            }

            const totalEntries = counterToNumber(response[columnFamily + "totaldataentries"])
            const totalSubmissions = counterToNumber(response[columnFamily + "totalsubmissions"])
            const responseRate = toPercent(totalSubmissions, totalEntries)
            const numPassedQA = counterToNumber(response[columnFamily + "totalpassqa"])
            const qaResponseRate =  toPercent(numPassedQA, totalSubmissions)

            console.log(counterToNumber(response[columnFamily + "totalfines"]))
            console.log(counterToNumber(response[columnFamily + "totaldeficiencies"]))

            res.render('facilities_info',{
                providerName : response[columnFamily + "providername"],
                providerAddress: response[columnFamily + "provideraddress"],
                providerCity: response[columnFamily + "providercity"],
                providerState: response[columnFamily + "providerstate"],
                providerZipCode : response[columnFamily + "providerzipcode"],
                totalResidentAdmissions : counterToNumber(response[columnFamily + "totalresidentcovidadmissions"]),
                totalResidentCases : counterToNumber(response[columnFamily + "totalresidentconfirmedcovid"]),
                totalResidentDeaths : counterToNumber(response[columnFamily + "totalresidentcoviddeaths"]),
                totalStaffCases : counterToNumber(response[columnFamily + "totalstaffconfirmedcovid"]),
                totalStaffDeaths : counterToNumber(response[columnFamily + "totalstaffcoviddeaths"]),
                ableTestResidentsWithinWeek : response[columnFamily + "abletestallresidentswithin7days"],
                avgTimeReceiveResidentTestResults : response[columnFamily + "avgtimetoreceiveresidenttestresults"],
                notTestingOther : response[columnFamily + "nottestingresidentsother"],
                notTestingUncertaintyReimbursement : response[columnFamily + "nottestingresidentsuncertaintyreimbursement"],
                percentOccupied : percentOccupied,
                percentVentilators : percentVentilators,
                oneWeekSupplyEyeProtection : response[columnFamily + "oneweeksupplyeyeprotection"],
                oneWeekSupplyGloves : response[columnFamily + "oneweeksupplygloves"],
                oneWeekSupplyGowns : response[columnFamily + "oneweeksupplygowns"],
                oneWeekSupplyHandSanitizer : response[columnFamily + "oneweeksupplyhandsanitizer"],
                oneWeekSupplyN95Masks : response[columnFamily + "oneweeksupplyn95masks"],
                oneWeekSupplySurgicalMasks : response[columnFamily + "oneweeksupplysurgicalmasks"],
                severeDeficiencies : counterToNumber(response[columnFamily + "severedeficiencies"]),
                severeInfectionDeficiences : counterToNumber(response[columnFamily + "severeinfectiondeficiencies"]),
                shortageAides : response[columnFamily + "shortageaides"],
                shortageClinicalStaff : response[columnFamily + "shortageclinicalstaff"],
                shortageNursingStaff : response[columnFamily + "shortagenursingstaff"],
                shortageOtherStaff : response[columnFamily + "shortageotherstaff"],
                testedAsymptomaticResidentsAsSurveillance : response[columnFamily + "testedasymptomaticresidentsassurveillance"],
                testedAsymptomaticResidentsFacilityWide : response[columnFamily + "testedasymptomaticresidentsfacilitywideafternewcase"],
                testedAsymptomaticResidentsInUnit : response[columnFamily + "testedasymptomaticresidentswithinunitafternewcase"],
                testedResidentsWithNewSignsOrSymptoms : response[columnFamily + "testedresidentswithnewsignsorsymptoms"],
                responseRate : responseRate,
                infectionDeficiencies : counterToNumber(response[columnFamily + "infectiondeficiencies"]),
                totalDeficiencies : counterToNumber(response[columnFamily + "totaldeficiencies"]),
                qaResponseRate : qaResponseRate,
                totalFines : counterToNumber(response[columnFamily + "totalfines"])


            })
        })
    })

})

app.get('/state_list/', function (req, res) {
    console.log(req.query)
    const state = req.query['states'];
    console.log(state);
    res.render('stateList')
})

app.get('/submit_data/', function (req, res) {
        const numRecords = req.query['limit']
        fetch(`https://data.cms.gov/resource/s2uc-8wxp.json?$where=week_ending>'2020-11-15'&$limit=${numRecords}`).then(
            response => response.json()).then(data => data.forEach(
            record => {
                console.log(record)
                // const datePattern = /(\d{4})-(\d{2})-(\d{2})/;
                // const dateArray = record['week_ending'].match(datePattern);
                // console.log(dateArray);
                // const reportedYear = dateArray[1];
                // const reportedMonth = dateArray[2];
                // const reportedDay = dateArray[3];
                const federalProviderNumber = record['federal_provider_number'];
                const resAdmissions = parseInt(record['residents_weekly_admissions']);
                const resConfirmed = parseInt(record['residents_weekly_confirmed']);
                const resDeaths = parseInt(record['residents_weekly_covid_19']);
                const staffConfirmed = parseInt(record['staff_weekly_confirmed_covid']);
                const staffDeaths = parseInt(record['staff_weekly_covid_19_deaths']);

                var report = {
                    // reportedYear: reportedYear,
                    // reportedMonth: reportedMonth,
                    // reportedDay: reportedDay,
                    federalProviderNumber: federalProviderNumber,
                    resAdmissions: resAdmissions,
                    resConfirmed: resConfirmed,
                    resDeaths: resDeaths,
                    staffConfirmed: staffConfirmed,
                    staffDeaths: staffDeaths
                }

                kafkaProducer.send([{topic: 'christiannenic-nursing-covid', messages: JSON.stringify(report)}],
                    function (err, data) {
                        console.log("Kafka Error: " + err)
                        console.log(data);
                        console.log(report);

                    })

            }))
    res.send("Data written to Kafka");
    });

app.get('/submit_manual_data/', function(req, res){
    const facility = req.query['facility']
    hclient.table('christiannenic_facilities_list').row(facility).get(function (err, cells){
        const facilityFpn = rowToMap(cells);
        const fpn = facilityFpn['facility:federalprovidernumber']

        const report = {
            federalProviderNumber: fpn,
            resAdmissions: req.query['resAdmissions'],
            resConfirmed: req.query['resConfirmed'],
            resDeaths: req.query['resDeaths'],
            staffConfirmed: req.query['staffConfirmed'],
            staffDeaths: req.query['staffConfirmed']
        }
            kafkaProducer.send([{topic: 'christiannenic-nursing-covid', messages: JSON.stringify(report)}],
                function (err, data) {
                    console.log("Kafka Error: " + err)
                    console.log(data);
                    console.log(report);

                })
        res.send("Data written to Kafka");
    }


        )
});





app.listen(port);
