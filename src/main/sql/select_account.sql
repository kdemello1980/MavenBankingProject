SELECT a.id "a.id", a.balance "a.balance", s.id "s.id", s.status "s.status", t.id "t.id", t.type "t.type"
FROM accounts a, account_status s, account_types t
WHERE a.status = s.id AND a.type = t.id;
