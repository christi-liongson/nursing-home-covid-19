cd /home/ec2-user/christiannenic/nursing_covid_investigator
forever --id "christiannenic_nursing_covid_investigator" start app.js 3016 ip-172-31-11-144.us-east-2.compute.internal 8070 b-2.mpcs53014-kafka.fwx2ly.c4.kafka.us-east-2.amazonaws.com:9092,b-1.mpcs53014-kafka.fwx2ly.c4.kafka.us-east-2.amazonaws.com:9092
