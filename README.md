
```sql
SELECT * FROM PLAYER_PROFILE  p
inner join CLAN c on c.id = p.clan_id
inner join DEVICE d on d.player_id = p.player_id;
```


```sql
SELECT * from Campaign ca
inner join Matcher m on ca.matcher_id = m.id;
```
