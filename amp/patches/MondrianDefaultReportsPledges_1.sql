DELETE FROM off_line_reports;
INSERT INTO `off_line_reports` (`id`, `name`, `query`, `teamid`, `ispublic`, `measures`, `columns`, `creationdate`, `ownerId`, `type`) VALUES 
  (1, 'Report by Sectors', 'select NON EMPTY {[Measures].[Actual Commitments], [Measures].[Actual Disbursements], [Measures].[Actual Expenditures]} ON COLUMNS, NON EMPTY {([Primary Sector].[All Primary Sectors], [Activity].[All Activities])} ON ROWS from [Donor Funding]', NULL, True, 'Actual Commitments,Actual Disbursements,Actual Expenditures,sector Percentage ', 'Primary Sector,Primary Sectors,Activities', NULL, NULL, 1),
  (2, 'Report by Financing Instrument and Donor Information', 'select NON EMPTY Crossjoin({[Donor Dates]}, {[Measures].[Actual Commitments]}) ON COLUMNS, NON EMPTY Crossjoin({[Financing Instrument]}, Crossjoin({[Terms of Assistance]}, Crossjoin({[Donor]}, Crossjoin({[Primary Sector]}, {[Activity]})))) ON ROWS from [Donor Funding]', NULL, True, 'Donor Dates,Actual Commitments', 'Financing Instrument,Terms of Assistance,Donor,Primary Sector', NULL, NULL, 1),
  (3, 'Report by Donors', 'select NON EMPTY {[Measures].[Actual Commitments]} ON COLUMNS, {[Donor]} ON ROWS from [Donor Funding]', NULL, False, 'Actual Commitments', 'Donor', NULL, NULL, 1),
  (4, 'Report by funding years', 'select NON EMPTY {[Measures].[Actual Commitments], [Measures].[Actual Disbursements], [Measures].[Planned Commitments], [Measures].[Planned Disbursements]} ON COLUMNS, {[Donor Dates].[All Periods]} ON ROWS from [Donor Funding]', NULL, False, 'Actual Commitments,Actual Disbursements,Planned Commitments,Planned Disbursements', 'Donor Dates', NULL, NULL, 1),
  (11, 'Donor Aid Modality', 'select NON EMPTY Hierarchize(Crossjoin({[Measures].[Pledges]}, Union({[Aid Modality].[All Aid Modalitys]}, [Aid Modality].[All Aid Modalitys].Children))) ON COLUMNS,\n  {[Donor].[All Donors], [Donor].[All Donors].[Agence de Coopération Internationale Royaume Uni], [Donor].[All Donors].[Agence Française de Développement]} ON ROWS\nfrom [Pledges Funding]\n', 6, NULL, 'pledge', 'Donor,Aid Modality', NULL, NULL, 2),
  (20, 'Pledges By Donor And Dates', 'select NON EMPTY Hierarchize(Union(Crossjoin({[Measures].[Pledges]}, {[Pledges Dates].[All Periods]}), Crossjoin({[Measures].[Pledges]}, [Pledges Dates].[All Periods].Children))) ON COLUMNS,\n  {[Donor].[All Donors], [Donor].[All Donors].[Agence de Coopération Internationale Royaume Uni], [Donor].[All Donors].[Agence Française de Développement]} ON ROWS\nfrom [Pledges Funding]\n', 6, NULL, 'Pledges Total', 'Pledges Dates,Donor', NULL, NULL, 2),
  (21, 'Donor Sector by Dates', 'select NON EMPTY Hierarchize(Crossjoin({[Measures].[Pledges]}, Union({[Pledges Dates].[All Periods]}, [Pledges Dates].[All Periods].Children))) ON COLUMNS,\n  NON EMPTY Hierarchize(Union(Union(Union({([Donor].[All Donors], [Primary Sector].[All Primary Sectors])}, Crossjoin([Donor].[All Donors].Children, {[Primary Sector].[All Primary Sectors]})), Crossjoin({[Donor].[All Donors].[Agence Française de Développement]}, [Primary Sector].[All Primary Sectors].Children)), Crossjoin({[Donor].[All Donors].[Agence de Coopération Internationale Royaume Uni]}, [Primary Sector].[All Primary Sectors].Children))) ON ROWS\nfrom [Pledges Funding]\n', 6, NULL, 'Pledges Total', 'Pledges Dates,Primary Sector,Donor', NULL, NULL, 2),
  (30, 'Pledges vs Commitments', 'select NON EMPTY {([Measures].[Actual Commitments], [Pledges Dates].[All Periods]), ([Measures].[Pledges], [Pledges Dates].[All Periods]), ([Measures].[Commitment Gap], [Pledges Dates].[All Periods])} ON COLUMNS,\n  NON EMPTY {([Title], [Pledge Type], [Donor], [Aid Modality], [Type of Assistance])} ON ROWS\nfrom [Donor and Pledges]\n', 6, NULL, 'Commitment Gap,Actual Commitments,Pledges Total', 'Pledges Dates,Pledge Type,Title,Donor,Aid Modality', NULL, NULL, 2),
  (31, 'Pledges Totals, Commitments and Disbursements', 'select NON EMPTY Crossjoin({[Measures].[Actual Commitments], [Measures].[Pledges], [Measures].[Actual Disbursements]}, {[Pledges Dates].[All Periods]}) ON COLUMNS,\r\n  NON EMPTY {([Title], [Pledge Type], [Donor], [Aid Modality], [Type of Assistance])} ON ROWS\r\nfrom [Donor and Pledges]\r\n', 6, NULL, 'Actual Commitments,Pledges Total,Actual Disbursements', 'Pledges Dates,Pledge Type,Title,Donor,Aid Modality', NULL, NULL, 2);

COMMIT;