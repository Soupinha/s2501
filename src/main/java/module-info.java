module com.mycompany.s2501 {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires java.sql;
    //requires org.postgresql.jdbc;

    opens com.mycompany.s2501 to javafx.fxml;
    opens com.mycompany.s2501.Quarto to javafx.fxml;
    opens com.mycompany.s2501.CorredorP to javafx.fxml;
    opens com.mycompany.s2501.Fora to javafx.fxml;
    opens com.mycompany.s2501.Corredor to javafx.fxml;
    opens com.mycompany.s2501.Banheiro to javafx.fxml;
    opens com.mycompany.s2501.Cozinha to javafx.fxml;
    opens com.mycompany.s2501.Sala to javafx.fxml;
    
    
    exports com.mycompany.s2501;
}
