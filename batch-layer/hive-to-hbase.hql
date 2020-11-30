-- Writing christiannenic_facilities_list_hive hive table to hbase

create external table  christiannenic_facilities_list (
  provider_name string, federalprovidername string)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ('hbase.columns.mapping' = ':key,info:federalprovidername')
TBLPROPERTIES ('hbase.table.name' = 'christiannenic_facilities_list');

insert overwrite table christiannenic_facilities_list
  select  * from christiannenic_facilities_list_hive;