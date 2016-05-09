# pb138-evidence-prijmu-web

V aplikační logice jsou zatím implementovány pouze create, upate a delete metody u entity Employee.
Budou přidány ještě retrieve metody a obdobně potom vše u dalších entit - Work a Record.

O práci s fakturami zatím nemám jasnou představu.

Za balíčku xmlEdit se využívá pouze třída executeQuery, další dvě jsou na jednorázové přidání dokumentu a jednorázové vyhodnocení XQuery dotazu.
Aby to fungovalo, je třeba mít nainstalovanou a spuštěnou eXist databázi a ve třídě ExecuteQuery ve stejnojmenné metodě nastavit atributy pro přístup do databáze.
(přednastavený je xml soubor WorkEvidence, který tam musí být. Já ho měl v kolekci sample.)
