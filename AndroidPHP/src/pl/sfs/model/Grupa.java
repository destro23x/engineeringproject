package pl.sfs.model;

public class Grupa {
    public String Grupy_Aktywny;

    public Integer Grupy_ID;

    public String Grupy_Nazwa;

    public String Grupy_Stopien;

    public Integer Kursy_ID;

    public Integer Pracownik_ID;

    public Grupa(){    	
    }
    
    public String getGrupy_Aktywny() {
		return Grupy_Aktywny;
	}
	
	public void setGrupy_Aktywny(String grupy_Aktywny) {
		Grupy_Aktywny = grupy_Aktywny;
	}

	public Integer getGrupy_ID() {
		return Grupy_ID;
	}

	public void setGrupy_ID(Integer grupy_ID) {
		Grupy_ID = grupy_ID;
	}

	public String getGrupy_Nazwa() {
		return Grupy_Nazwa;
	}

	public void setGrupy_Nazwa(String grupy_Nazwa) {
		Grupy_Nazwa = grupy_Nazwa;
	}

	public String getGrupy_Stopien() {
		return Grupy_Stopien;
	}

	public void setGrupy_Stopien(String grupy_Stopien) {
		Grupy_Stopien = grupy_Stopien;
	}

	public Integer getKursy_ID() {
		return Kursy_ID;
	}

	public void setKursy_ID(Integer kursy_ID) {
		Kursy_ID = kursy_ID;
	}

	public Integer getPracownik_ID() {
		return Pracownik_ID;
	}

	public void setPracownik_ID(Integer pracownik_ID) {
		Pracownik_ID = pracownik_ID;
	}
    
    
}
