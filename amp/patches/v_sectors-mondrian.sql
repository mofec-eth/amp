CREATE OR REPLACE VIEW `v_sectors` AS select `sa`.`amp_activity_id` AS `amp_activity_id`,`getSectorName`(`getParentSectorId`(`s`.`amp_sector_id`)) AS `sectorname`,`getParentSectorId`(`s`.`amp_sector_id`) AS `amp_sector_id`,sum(`sa`.`sector_percentage`) AS `sector_percentage`,`s`.`amp_sec_scheme_id` AS `amp_sector_scheme_id`, ss.sec_scheme_name from ((`amp_activity_sector` `sa` join `amp_sector` `s`) join `amp_sector_scheme` `ss`) where ((`s`.`amp_sector_id` = `sa`.`amp_sector_id`) and `s`.`amp_sec_scheme_id` in (select `amp_classification_config`.`classification_id` AS `classification_id` from `amp_classification_config` where (`amp_classification_config`.`name` = _latin1'Primary')) and (`s`.`amp_sec_scheme_id` = `ss`.`amp_sec_scheme_id`)) group by `sa`.`amp_activity_id`,`getSectorName`(`getParentSectorId`(`s`.`amp_sector_id`)) order by `sa`.`amp_activity_id`,`getSectorName`(`getParentSectorId`(`s`.`amp_sector_id`));