/*
Si vuole realizzare una piattaforma per la gestione dei viaggi di lavoro aziendali. Realizzare un web service Spring Boot, completo di database PostgreSQL, che permetta di gestire i seguenti elementi:

		Viaggio: rappresenta un viaggio di lavoro. È caratterizzato da una destinazione, una data ed uno stato (in programma, completato).

		Dipendente: rappresenta un dipendente che può essere assegnato a un viaggio. Ogni dipendente ha uno username, nome, cognome ed email.

		Bisogna inoltre gestire la prenotazione di un viaggio per un dipendente. Ogni prenotazione ha un riferimento al viaggio, una data di richiesta, e può includere note o preferenze per il dipendente (es. preferenza di volo, alloggio). Un dipendente non può avere più prenotazioni per lo stesso giorno.

Il web service deve esporre le operazioni CRUD per la gestione dei viaggi e dei dipendenti, oltre a un endpoint per consentire l'assegnazione di un dipendente a un viaggio.
Deve inoltre essere previsto un endpoint per modificare lo stato del viaggio (es. da "in programma" a "completato").
Infine bisogna permettere l’upload di un’immagine profilo per i dipendenti.

Il servizio deve gestire la convalida dei dati e prevenire la prenotazione di viaggi per dipendenti già impegnati in altre date. Devono essere gestiti gli errori con status code appropriati.

*/


package davide.project_week_6_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectWeek6BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectWeek6BackendApplication.class, args);
    }

}
