DEMO Küsitluste rakendus
========================

Käesolev rakendus on mõeldud küsitluste tegemiseks sarnaselt Doodle teenusega (https://doodle.com/).

Rakenduse eesmärk on võimaldada küsitluste koostamist, millele kasutajad saavad vastata.
Iga kasutaja esineb vastustes ainult ühe korra, kuid saab oma vastuseid hiljem muuta.
Vastused on järjend true/false formaadis iga küsimuse kohta.



Peamised URLid
--------------

 * http://localhost:8080/polls (backendi ülesande jaoks)
 * http://localhost:8080/menu (frontendi ülesande jaoks)



Backendi ülesanded
------------------

1. Tutvu rakenduse senise koodiga ning käivita rakendus (see kasutab mälus H2 andmebaasi, mille schema/andmed säilivad
  rakenduse sulgemiseni).
2. Täienda `PollRepository` klassi, kus on funktsionaalsuseta tühjad meetodid, et need tööle hakkaksid.
3. Täienda `PollService` klassi mõnede ärireeglitega, mida Sinu arvates võiks kontrollida enne salvestamist.
4. Täienda `PollController` klassi, et see toetaks `If-Modified-Since` headerit HTTP päringus ja, kui küsitluste andmed
   pole vahepeal muutunud, tagastaks `304 Not Modified`.
4. Iga endpointi kohta palun esitada ka `curl` käsk (https://curl.haxx.se/) selle välja kutsumiseks.

Koodi ja andmebaasi loogikat võib täiendamiseks julgelt muuta.

Ootame võimalikult minimaalset lahendust, milles hindame eelkõige töökindlust, lihtsust ja ühtset stiili
(REST-liidesest andmebaasini). Ebaselguse korral võib ka küsimusi esitada või kommenteerida vastav koht koodis.



REST-teenuste välja kutsumine
-----------------------------

1. `curl http://localhost:8080/menu`
2. `curl http://localhost:8080/polls`
3. `curl -X POST http://localhost:8080/polls -H "Content-Type: application/json" -d '{"title":"test", "options":["uno","dos","tres"], "owner": "weron" }'`
4. `curl http://localhost:8080/polls/{uuid}/`
5. `curl-X POST http://localhost:8080/polls/{uuid}/ -H "Content-Type: application/json" -d '{"title":"test43", "options":["one","two","three"], "owner": "norew", "active": true }'`
6. `curl -X POST http://localhost:8080/polls/f945b5e9-4253-42e0-b9a1-6905466b61bd/response -H "Content-Typ: application/json" -d '{"choices":[true, false, true], "owner": "weron" }'`