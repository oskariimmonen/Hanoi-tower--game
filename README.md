# Ohjelmointi 3: Projekti SISU:sta selvää
by @qksape, @pvosim ja @uusitaln

Ohjelman tarkempi dokumentaatio löytyy: [/Documentation/FinalDocument.pdf](Documentation/FinalDocument.pdf)

## Pika-ohje

Ohjelmaan voi kirjautua testitunnuksella ```123456``` / Testi Tunnari tai luoda oman tunnuksen alla olevilla ohjeilla.

Ohjelman käynnistyessä käyttäjälle aukeaa kirjautumisikkuna. Käyttäjä voi kirjautua sisään järjestelmään yksilöllisen opiskelijanumeron avulla. Opiskelijanumero kirjoitetaan tekstikenttään, jonka jälkeen kirjaudutaan painamalla kirjaudu sisään -nappia. Käyttäjä voi myös lopettaa ohjelman suorituksen painamalla ”Lopeta” tai avata uuden ikkunan painamalla ”Luo uusi käyttäjä”. Aukeavassa ikkunassa voidaan luoda uusi käyttäjä, jolle annetaan tiedoiksi nimi, opiskelijanumero, opintojen aloitusvuosi, arvioitu lopetusvuosi sekä opintosuunta. Käyttäjän on pakko syöttää nimi ja opiskelijanumero. Jos hän ei syötä, niin ohjelma tulostaa virheviestin, ja kirjautuminen ei etene. Muissa tiedoissa on automaattisesti jotakin, mutta käyttäjä voi tietenkin myös muokata näitä tietoja. 

Pääikkunaan eli ”MainWindowiin” päästään siis joko kirjautumalla opiskelijanumerolla tai luomalla uusi käyttäjä. Pääikkunassa ensimmäisellä välilehdellä käyttäjä näkee omat valitun opintosuuntansa mukaisen tutkintorakenteen ja kurssit. Käyttäjä voi merkitä kursseja suoritetuksi klikkaamalla ns. ”checkmarkin”  kurssin viereiseen laatikkoon ja täten seurata tutkintonsa etenemistä. Käyttäjä pystyy myös tarkastelemaan kurssin sisällön kuvausta viemällä kursorin halutun kurssin päälle. Toisella välilehdellä näkyvät käyttäjän tiedot, joita voi myös halutessaan muuttaa. Muutokset tallentuvat järjestelmän tietorakenteeseen. Ohjelman voi pääikkunassa lopettaa painamalla ”Lopeta”.

Ohjelman kohdatessa virheen asetustiedostossa ilmoitetaan käyttäjälle virheestä ja varoitetaan käyttäjää mahdollisesta tietojen katoamisesta, mikäli käyttäjä jatkaa tästä huolimatta.

## Tiedetyt ongelmat

- Tietojen ylikirjoittamisen vaara, uuden käyttäjän luonnissa ei tarkisteta, onko opiskelijanumero jo käytössä.
- Ohjelma vaatii internetyhteyden ja Sisu API:n tulee olla käytettävissä.
- Eri ympäristöissä yksikkötestien kääntymisessä häikkää. Nicklaksen ympäristössä poistettava module-info.java tiedostosta ```requires org.junit.jupiter.api;```
