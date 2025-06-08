module org.etf.unibl.danilo_todorovic_1156_22_pj {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.etf.unibl.danilo_todorovic_1156_22_pj to javafx.fxml;
    exports org.etf.unibl.danilo_todorovic_1156_22_pj.transport;
    exports org.etf.unibl.danilo_todorovic_1156_22_pj.rental;
    exports org.etf.unibl.danilo_todorovic_1156_22_pj.simulacija;
    exports org.etf.unibl.danilo_todorovic_1156_22_pj.util;
    exports org.etf.unibl.danilo_todorovic_1156_22_pj;
}