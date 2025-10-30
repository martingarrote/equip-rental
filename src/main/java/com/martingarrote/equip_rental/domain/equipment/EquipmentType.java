package com.martingarrote.equip_rental.domain.equipment;

public enum EquipmentType {
    DESKTOP("Computador de Mesa"),
    LAPTOP("Notebook"),
    MONITOR("Monitor"),
    KEYBOARD("Teclado"),
    MOUSE("Mouse"),
    PRINTER("Impressora"),
    SCANNER("Scanner"),
    PROJECTOR("Projetor"),
    SERVER("Servidor"),
    NETWORK_SWITCH("Switch de Rede"),
    ROUTER("Roteador"),
    MODEM("Modem"),
    UPS("Nobreak / UPS"),
    EXTERNAL_STORAGE("Armazenamento Externo"),
    WEBCAM("Webcam"),
    HEADSET("Headset"),
    MICROPHONE("Microfone"),
    SPEAKERS("Caixas de Som"),
    TABLET("Tablet"),
    SMARTPHONE("Smartphone"),
    DOCKING_STATION("Dock Station"),
    GRAPHIC_TABLET("Mesa Digitalizadora"),
    VR_HEADSET("Óculos de Realidade Virtual"),
    OTHER("Outro");

    private final String description;

    EquipmentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
