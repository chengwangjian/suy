/*修改warntypetable中w_mode==46的EnglishwarnType的内容*/
UPDATE warntypetable SET EnglishwarnType='<room>-<device> instantaneous current is more than 150A!' WHERE w_mode=46;