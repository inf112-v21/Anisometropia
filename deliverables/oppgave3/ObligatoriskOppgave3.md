## Anisometropia

**Total møtevarighet:** 9 timer

**Møtereferat:** Finnes på GitHub under wiki

**Dokumentasjon om teknisk produktoppsett:** Finnes i README.md

**Dokumentasjon om bugs:** Finnes i README.md

**Bilde av klassediagram:** Finnes i deliverables-mappen

**Oversikt over brukerhistorier, akseptansekriterier og arbeidsoppgaver:** Finnes på prosjektet sitt project board på GitHub

#### Roller i teamet
_Vi inkluderer også i denne innleveringen det hvert medlem skrev om sin eget ansvarsområde fra forrige levering._
*	_Lagleder_ (Henriette): Som lagleder er man nødt til å holde oversikt over hvilke arbeidsoppgaver som er gjennomført, og hvilke arbeidsoppgaver som trenger å gjennomføres før innlevering. Jeg gjør mitt beste for å tilrettelegge slik at alle er fornøyd og enige i de arbeidsoppgavene de blir tildelt og at arbeidsfordelingen er rettferdig. Videre så prøver jeg å holde god flyt i samtalen i møtene slik at vi får kommet oss igjennom de nødvendige punktene vi ønsker å snakke om. Jeg prøver og å sørge for at alle har en klar forståelse av hvilken arbeidsoppgaver de har.
*	_Testansvarlig_ (Lazar): To ensure that we are writing sensible code, I make sure that we have proper tests where necessary  for all the new code that we implement into our project. Upon checking test results I take notes about the ones that failed and communicate with the group (or specific people responsible for that piece of code) why test(s) could be failing and how the issue can be resolved.
*	_Overordnet ansvar for spillmekanikk_ (Bjørnar): Prøver å hele tiden opprettholde et overblikk på hele spillet, og spesielt det visuelle (og hvordan LibGDX viser ting visuelt), og se muligheter for abstrahering i koden vår. Gjør at jeg ofte raskt kan tenke meg måter å implementere nye ideer/ønsker vi har for vårt spill.
* 	_Ansvarlig for kodeverifikasjon_ (Petter): Her handler det om å ha god oversikt over klassene og interfacene, men også å ha et oversiktlig og forståelig mappesystem. Jeg passer på at det holdes god modularitet i koden og at refaktorering brukes der det er mulig. Fin kodestruktur og gode metoder vil hjelpe oss gjennom hele prosjektet da dette vil være med på å effektivisere arbeidet.
*	_Ansvarlig for brukerhistorier_ (Felix): Denne rollen innebærer å passe på at brukerhistorier på prosjektet omfatter en passende mengde arbeid og inneholder både arbeidsoppgavene som kreves og akseptansekriteriene for å kunne vurdere brukerhistorien som ferdigstilt.

Det har heller ikke vært noe behov denne gangen å oppdatere rollene i teamet ettersom at alle er fornøyde med de rollene og ansvarsområdene de har blitt tildelt. Som forrige gang så føler vi at de rollene vi har dekker flere viktige områder av prosjektet som har behov for ekstra organisering og oppmerksomhet, som nettopp brukerhistorier, testing og kodeverifikasjon.  Gruppen er derfor enig i at det ikke er noe behov så langt for å tilføye nye roller, eller for å omorganisere rollene. 

#### Prosjektmetodikk og organisering 

Den største forskjellen med hvordan vi har strukturert arbeidet denne gangen fra sist er at vi har tatt i bruk code with me i IntelliJ. Dette er noe vi ikke har hatt behov for tidligere ettersom at alle medlemmene av gruppen har klart å løse de oppgavene de har blitt tildelt mer eller mindre individuelt. Det som gjorde at vi bestemte oss for å bruke code with me nå var for å prøve å muliggjøre multiplayer over local area network. Lazar og Henriette har derfor parprogrammert sammen ved hjelp av code with me og deres samarbeid har ført til at gruppen har gjort store fremskritt med multiplayer.

Siden Lazar og Henriette gjorde suksess med code with me bestemte vi oss for å prøve å ta dette i bruk på et av møtene til teamet for å legge inn funksjonalitet slik at spillerne ikke skal kunne gå gjennom vegger. Dette var en fin aktivitet for gruppen fordi vi i større grad kunne diskutere hvordan de ulike klassene samhandler ettersom at alle bidragsyterne var til stede.
Vi bemerket oss og at det kanskje var litt i overkant å ha fem stykker som diskuterte et problem samtidig fordi dette ble noe ineffektivt, men dette er en god erfaring å ha med seg inn i siste delen av prosjektet ettersom at vi tenker å bruke code with me videre. Nå vet vi at to til tre medlemmer om samme problem er mer optimalt enn alle fem. Dette møtet med code with me ga uansett Petter et godt utgangspunkt for å gjøre ferdig implementasjonen av Wall.java og funksjonaliteten som hører til denne klassen. 

Videre så ønsker vi å tilføye her at vi enda er fornøyd med vårt valg om å bruke elementer fra Kanban i vår prosjektmetodikk og kort om hvorfor:
- Fleksibilitet passer gruppen vår bra og det har ført til at vi har kunnet organisere oss på en naturlig måte som passer bra for teamet.
- Noe som er sentralt for Kanban er å ha et godt project board, dette vil si at project boardet til enhver tid skal være oppdatert og at det skal være lett for de forskjellige medlemmene av gruppen å se hvilken oppgaver som er startet på, hva som skal gjøres videre, og hva som er fullført. Vårt project board har gjort det enkelt for gruppen å få en rask og god oversikt over status på prosjektet.
- Regelen om at ingen skal ha mer enn 3 oppgaver samtidig har ført til at alle har vært komfortable med mengden arbeid de har blitt tildelt. 

#### Gruppedynamikk og kommunikasjon 

Utenom vårt faste møtepunkt på onsdagene i seminaret så setter vi opp møter ved behov.
Vi har fremdeles 100% oppmøte noe som er en god indikasjon på at alle medlemmene viser interesse og initiativ for prosjektet. Under møtene går praten lett og vi opplever at alle får kommet til med sine innspill om det som diskuteres. Utenom møtene så holder vi kontakt på discord. Vi opplever at gruppen fungerer godt i lag, men vi har litt bekymring for at vi må bli enda flinkere til å kommunisere hvordan hvert enkelt medlem jobber med prosjektet. Dette er hovedsakelig fordi medlemmene i større grad har jobbet individuelt med oppgavene sine enn sammen. Vi kommer tilbake til dette punktet i retrospektiv og forbedringspotensiale. 

#### Retrospektiv og forbedringspotensiale 

Forbedringspunktene vår fra forrige gang var som følger:
1. Holde project board oppdatert til enhver tid, og ikke bare rett før innlevering
    - Vi har hatt stor fremgang siden sist når det kommer til å følge opp project boardet underveis i utviklingsprosessen. Dette har vært klar fordel for gruppen ettersom at det har vært mye mer oversiktlig å se hvordan status er på arbeidsoppgavene.
2. Bli flinkere til å sammen planlegge strukturen av prosjektet før vi setter i gang slik at alle starter arbeidsoppgavene sine med en forståelse av hvordan de forskjellige klassene skal interagere. 
    - Under forrige innlevering så hadde vi problemer med at strukturen på prosjektet sklidde ut. Dette førte til at vi hadde en opprydning av prosjektet like før sist innlevering. Alle bidrog i denne prosessen ved å sammen diskutere hvordan det ble best å strukturere klassene og hvordan vi ønsket at de skulle samhandle. Vi har enda litt arbeid å gjøre før vi kan si oss fornøyd med strukturen, men forskjellen nå fra sist gang er at vi alle har en forståelse av hvordan vi ønsker å løse dette. Det er for eksempel snakk om å lage en abstrakt klasse med default metoder som håndterer det meste av spill logikken, videre så skal klasser som GameLogic og MultiPlayerLogic extende denne logikk klassen. Dette er noe vi er kommet frem til sammen.
3. Skrive javadoc til koden slik at det blir lettere for medlemmene av teamet å sette seg inn i andre sin kode.
    - Dette er et punkt som enda har mye forbedringspotensiale ettersom at det enda mangler god dokumentasjon av koden flere steder i prosjektet. Selv om koden har god lesbarhet er det fremdeles nyttig med dokumentasjon som gjør det enda lettere å sette seg inn i hvordan de ulike er implementert, spesielt når man ikke har skrevet den selv.  Blir vi flinkere på dette punktet så vil det effektivisere hvordan vi jobber med prosjektet ettersom at det blir lettere å få en oversikt over koden. 

Selv om vi har forbedret oss fra sist innlevering til denne så har vi enda litt å gå på, hovedsakelig når det kommer til å hjelpe hverandre med å sette hverandre inn i arbeidet som vi selv har gjort. Vi tenker at dette er et problem som spesielt angår oss siden vi har jobbet mye individuelt. Hovedårsaken til at dette er noe som bekymrer oss er at programmet blir mer komplekst for hver gang vi legger til mer funksjonalitet noe som kan gjøre det vanskelig å legge inn ny funksjonalitet som trenger informasjon fra flere klasser med ulike forfattere. Det er derfor viktig at vi tar tak i dette så fort som mulig. En måte som vi tror kan bidra til å løse dette effektivt er å ta i bruk parprogrammering når vi skal realisere et mål som krever at klasser med forskjellige forfattere skal samhandle. Da kan forfatterne av de aktuelle klassene gå i lag for å finne ut hvordan de skal løse dette ved hjelp av parprogrammering. 

#### Forbedringspunkter fra retrospektiv 
- Skrive javadoc til koden slik at det blir lettere for medlemmene av teamet å sette seg inn i andre sin kode
- Flytte fokus fra individuelt arbeid til mer samarbeid gjennom parprogrammering
- Strukturere arbeidsoppgaver som omhandler ny funksjonalitet etter prioritet 


Vi har heller ikke til denne gang muliggjort multiplayer over local area network, men vi er kommet skikkelig godt i gang! Vi er motiverte til å få det til ved hjelp av peer-to-peer og kommer derfor ikke til å gi oss med det første. Det vi har fått til er å sende kort fra serveren til klienten, klienten klarer og å motta disse kortene med suksess. Klienten klarer på samme måte som serveren og sende sine kort, og de blir og mottatt av server. Det som gjenstår nå er å få dette til å samhandle med spill logikken som vi allerede har implementert.

Ny funksjonalitet vi har lagt til:
- Lasere tar nå skade på spillerne
- Spillerne har ikke mulighet til å gå gjennom vegger
- Det er laget en knapp for å hoste et spill, samt en join knapp for å bli med. Denne funksjonaliteten angår multiplayer
- Vi har og laget en power down knapp, denne har noen bugs
- Det er laget en meny for spillet
- Spillersymbolene er nå oppdatert slik at hver spiller har forskjellig symbol
- Det er lagt til begrensinger på hvordan spiller får velge kort i henhold til hvor mye skade den har tatt

Det som var av høyeste prioritet til denne innleveringen var ikke ny funksjonalitet, men opprydding og debugging av koden. Vi har derfor brukt en god del tid på forbedre allerede implementert kode før vi la til ny kode. Det var derfor ikke før de største bugsene ble fjernet at vi satt igang med å legge til ny funksjonalitet. 

#### Commits
Henriette har denne gangen ikke committed like mye som de andre medlemmene av teamet noe som kan forklares ved at hun og Lazar har jobbet sammen ved hjelp av code with me. Det har jobbet sammen i Lazar sitt prosjekt noe som har ført til at Lazar har pushet arbeidet som de har gjort sammen.

