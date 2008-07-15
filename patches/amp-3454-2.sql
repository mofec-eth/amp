CREATE OR REPLACE VIEW `v_donor_funding` AS select `f`.`amp_activity_id` AS `amp_activity_id`,`f`.`amp_funding_id` AS `amp_funding_id`,`fd`.`amp_fund_detail_id` AS `amp_fund_detail_id`,`d`.`name` AS `donor_name`,`fd`.`transaction_type` AS `transaction_type`,`fd`.`adjustment_type` AS `adjustment_type`,`fd`.`transaction_date` AS `transaction_date`,`fd`.`transaction_amount` AS `transaction_amount`,`c`.`currency_code` AS `currency_code`,`cval`.`category_value` AS `terms_assist_name`,`fd`.`fixed_exchange_rate` AS `fixed_exchange_rate`,`b`.`org_grp_name` AS `org_grp_name`,`ot`.`org_type` AS `donor_type_name`,`cval2`.`category_value` AS `financing_instrument_name`,`b`.`amp_org_grp_id` AS `org_grp_id`,`ot`.`amp_org_type_id` AS `org_type_id`,`cval2`.`id` as financing_instrument_id, `cval`.`id` AS `terms_assist_id` from (((((((`amp_funding` `f` join `amp_funding_detail` `fd`) join `amp_category_value` `cval`) join `amp_currency` `c`) join `amp_organisation` `d`) join `amp_org_group` `b`) join `amp_org_type` `ot`) join `amp_category_value` `cval2`) where ((`cval2`.`id` = `f`.`financing_instr_category_value_id`) and (`c`.`amp_currency_id` = `fd`.`amp_currency_id`) and (`f`.`amp_funding_id` = `fd`.`AMP_FUNDING_ID`) and (`cval`.`id` = `f`.`type_of_assistance_category_value_id`) and (`d`.`amp_org_id` = `f`.`amp_donor_org_id`) and (`d`.`org_grp_id` = `b`.`amp_org_grp_id`) and (`ot`.`amp_org_type_id` = `d`.`org_type_id`)) order by `f`.`amp_activity_id`;
CREATE OR REPLACE VIEW `v_contribution_funding` AS select `eu`.`amp_activity_id` AS `amp_activity_id`,`eu`.`id` AS `amp_funding_id`,`euc`.`id` AS `amp_funding_detail_id`,`o`.`name` AS `donor_name`,`euc`.`amount` AS `transaction_amount`,`euc`.`transaction_date` AS `transaction_date`,`c`.`currency_code` AS `currency_code`,`acv_term`.`category_value` AS `terms_assist_name`,`acv_mod`.`category_value` AS `financing_instrument_name`,`o`.`amp_org_id` AS `amp_org_id`,`o`.`org_grp_id` AS `org_grp_id`,`acv_term`.`id` AS `terms_assist_id` from (((((`amp_eu_activity` `eu` join `amp_eu_activity_contributions` `euc`) join `amp_currency` `c`) join `amp_category_value` `acv_term`) join `amp_category_value` `acv_mod`) join `amp_organisation` `o`) where ((`eu`.`id` = `euc`.`eu_activity_id`) and (`euc`.`amount_currency` = `c`.`amp_currency_id`) and (`acv_term`.`id` = `euc`.`financing_type_categ_val_id`) and (`acv_mod`.`id` = `euc`.`financing_instr_category_value_id`) and (`o`.`amp_org_id` = `euc`.`donor_id`)) order by `eu`.`amp_activity_id`;