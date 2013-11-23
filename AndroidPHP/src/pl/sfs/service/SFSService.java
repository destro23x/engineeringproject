package pl.sfs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;

import pl.sfs.model.Grupa;
import pl.sfs.model.Grupa_Kurs;
import pl.sfs.model.Klient;
import pl.sfs.model.Konto;
import pl.sfs.model.Kurs;
import pl.sfs.model.Oplata;
import pl.sfs.model.Powiadomienie;
import pl.sfs.model.Pracownik;
import pl.sfs.model.Wydarzenie;

/***
 * Klasa odpowiedzialna za obsluge metod zdalnych z uslugi SFService
 * 
 * @author Marcin Peck <marcinpeck@gmail.com>
 *
 */
public class SFSService {
	/***
	 * Pobiera liste grup
	 * 
	 * @return ArrayList<Grupa> Lista grup
	 * @throws Exception
	 */
	public ArrayList<Grupa> getGroups() throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Grupy_";
		
		ArrayList<Grupa> groups = new ArrayList<Grupa>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, null);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject g = (SoapObject) response.getProperty(i);
			
			Grupa tmpGroup = new Grupa();
			
			tmpGroup.setGrupy_ID(Integer.parseInt(g.getPrimitivePropertyAsString(tableName+"ID")));
			tmpGroup.setGrupy_Aktywny(g.getPrimitivePropertyAsString(tableName+"Aktywny"));
			tmpGroup.setGrupy_Nazwa(g.getPrimitivePropertyAsString(tableName+"Nazwa"));
			tmpGroup.setGrupy_Stopien(g.getPrimitivePropertyAsString(tableName+"Stopien"));
			tmpGroup.setKursy_ID(Integer.parseInt(g.getPrimitivePropertyAsString("Kursy_ID")));
			tmpGroup.setPracownik_ID(Integer.parseInt(g.getPrimitivePropertyAsString("Pracownik_ID")));
			
			groups.add(tmpGroup);
		}
		
		return groups;
	}
	
	/***
	 * Pobiera liste grup dla konkretnego pracownika
	 * 
	 * @param workerId Id pracownika
	 * @return ArrayList<Grupa_Kurs> Lista grup wraz z nazwami kursow
	 * @throws Exception
	 */
	public ArrayList<Grupa_Kurs> getGroupsForUser(int workerId) throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Grupy_";
		
		Map<Integer, Object[]> params = new HashMap<Integer, Object[]>();
		
		params.put(1, new Object[]{"workerId", workerId});
		
		ArrayList<Grupa_Kurs> groups = new ArrayList<Grupa_Kurs>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, params);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject g = (SoapObject) response.getProperty(i);
			
			Grupa_Kurs tmpGroup = new Grupa_Kurs();
			
			tmpGroup.setGrupy_ID(Integer.parseInt(g.getPrimitivePropertyAsString(tableName+"ID")));
			tmpGroup.setGrupy_Aktywny(g.getPrimitivePropertyAsString(tableName+"Aktywny"));
			tmpGroup.setGrupy_Nazwa(g.getPrimitivePropertyAsString(tableName+"Nazwa"));
			tmpGroup.setGrupy_Stopien(g.getPrimitivePropertyAsString(tableName+"Stopien"));
			tmpGroup.setKursy_ID(Integer.parseInt(g.getPrimitivePropertyAsString("Kursy_ID")));
			tmpGroup.setPracownik_ID(Integer.parseInt(g.getPrimitivePropertyAsString("Pracownik_ID")));
			tmpGroup.setKursy_Nazwa(g.getPrimitivePropertyAsString("Kursy_Nazwa"));
			
			groups.add(tmpGroup);
		}
		
		return groups;
	}
	
	/***
	 * Pobiera liste klientow
	 * @return ArrayList<Klient> Lista klientow
	 * @throws Exception
	 */
	public ArrayList<Klient> getClients() throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Klienci_";
		
		ArrayList<Klient> clients = new ArrayList<Klient>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, null);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject c = (SoapObject) response.getProperty(i);
			
			Klient tmpClient = new Klient();
			
			tmpClient.setKlienci_Aktywni(c.getPrimitivePropertyAsString(tableName+"Aktywni"));
			tmpClient.setKlienci_ID(Integer.parseInt(c.getPrimitivePropertyAsString(tableName+"ID")));
			tmpClient.setKlienci_Imie(c.getPrimitivePropertyAsString(tableName+"Imie"));
			tmpClient.setKlienci_Kod(c.getPrimitivePropertyAsString(tableName+"Kod"));
			tmpClient.setKlienci_Mail(c.getPrimitivePropertyAsString(tableName+"Mail"));
			tmpClient.setKlienci_Miasto(c.getPrimitivePropertyAsString(tableName+"Miasto"));
			tmpClient.setKlienci_Nazwisko(c.getPrimitivePropertyAsString(tableName+"Nazwisko"));
			tmpClient.setKlienci_Numer_domu(c.getPrimitivePropertyAsString(tableName+"Numer_domu"));
			tmpClient.setKlienci_Pesel(c.getPrimitivePropertyAsString(tableName+"Pesel"));
			tmpClient.setKlienci_Plec(c.getPrimitivePropertyAsString(tableName+"Plec"));
			tmpClient.setKlienci_Telefon(c.getPrimitivePropertyAsString(tableName+"Telefon"));
			tmpClient.setKlienci_Ulica(c.getPrimitivePropertyAsString(tableName+"Ulica"));
			
			clients.add(tmpClient);
		}
		
		return clients;
	}
	
	/***
	 * Pobiera liste klientow przypisanych do konkretnej grupy
	 * 
	 * @param groupId Id grupy
	 * @return ArrayList<Klient> Lista klientow w danej grupie
	 * @throws Exception
	 */
	public ArrayList<Klient> getClientsAssingToGroup(int groupId) throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Klienci_";
		
		ArrayList<Klient> clients = new ArrayList<Klient>();
		
		Map<Integer, Object[]> params = new HashMap<Integer, Object[]>();
		
		params.put(1, new Object[]{"groupID", groupId});
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, params);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject c = (SoapObject) response.getProperty(i);
			
			Klient tmpClient = new Klient();
			
			tmpClient.setKlienci_Aktywni(c.getPrimitivePropertyAsString(tableName+"Aktywni"));
			tmpClient.setKlienci_ID(Integer.parseInt(c.getPrimitivePropertyAsString(tableName+"ID")));
			tmpClient.setKlienci_Imie(c.getPrimitivePropertyAsString(tableName+"Imie"));
			tmpClient.setKlienci_Kod(c.getPrimitivePropertyAsString(tableName+"Kod"));
			tmpClient.setKlienci_Mail(c.getPrimitivePropertyAsString(tableName+"Mail"));
			tmpClient.setKlienci_Miasto(c.getPrimitivePropertyAsString(tableName+"Miasto"));
			tmpClient.setKlienci_Nazwisko(c.getPrimitivePropertyAsString(tableName+"Nazwisko"));
			tmpClient.setKlienci_Numer_domu(c.getPrimitivePropertyAsString(tableName+"Numer_domu"));
			tmpClient.setKlienci_Pesel(c.getPrimitivePropertyAsString(tableName+"Pesel"));
			tmpClient.setKlienci_Plec(c.getPrimitivePropertyAsString(tableName+"Plec"));
			tmpClient.setKlienci_Telefon(c.getPrimitivePropertyAsString(tableName+"Telefon"));
			tmpClient.setKlienci_Ulica(c.getPrimitivePropertyAsString(tableName+"Ulica"));
			
			clients.add(tmpClient);
		}
		
		return clients;
	}
	
	/***
	 * Pobiera liste kont uzytkownikow
	 * 
	 * @return ArrayList<Konto> Lista kont uzytkownikow
	 * @throws Exception
	 */
	public ArrayList<Konto> getAccounts() throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Konta_";
		
		ArrayList<Konto> accounts = new ArrayList<Konto>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, null);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject a = (SoapObject) response.getProperty(i);
			
			Konto tmpAccount = new Konto();
			
			tmpAccount.setKonta_ID(Integer.parseInt(a.getPrimitivePropertyAsString(tableName+"ID")));
			tmpAccount.setKonta_Uzytkownik(a.getPrimitivePropertyAsString(tableName+"Uzytkownik"));
			tmpAccount.setKonta_Haslo(a.getPrimitivePropertyAsString(tableName+"Haslo"));
						
			accounts.add(tmpAccount);
		}
		
		return accounts;
	}
	
	/***
	 * Pobiera liste kursow
	 * 
	 * @return ArrayList<Kurs> Lista kursow
	 * @throws Exception
	 */
	public ArrayList<Kurs> getCourses() throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Kursy_";
		
		ArrayList<Kurs> courses = new ArrayList<Kurs>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, null);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject c = (SoapObject) response.getProperty(i);
			
			Kurs tmpCourse = new Kurs();
			
			tmpCourse.setKursy_Aktywne(c.getPrimitivePropertyAsString(tableName+"Aktywne"));
			tmpCourse.setKursy_ID(Integer.parseInt(c.getPrimitivePropertyAsString(tableName+"ID")));
			tmpCourse.setKursy_Mnemonik(c.getPrimitivePropertyAsString(tableName+"Mnemonik"));
			tmpCourse.setKursy_Nazwa(c.getPrimitivePropertyAsString(tableName+"Nazwa"));
						
			courses.add(tmpCourse);
		}
		
		return courses;
	}
	
	/***
	 * Pobiera liste platnosci w systemie
	 * 
	 * @return ArrayList<Oplata> Lista platnosci
	 * @throws Exception
	 */
	public ArrayList<Oplata> getPayments() throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Oplaty_";
		
		ArrayList<Oplata> payments = new ArrayList<Oplata>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, null);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject p = (SoapObject) response.getProperty(i);
			
			Oplata tmpPayment = new Oplata();
			
			tmpPayment.setKlienci_ID(Integer.parseInt(p.getPrimitivePropertyAsString("Klienci_ID")));
			tmpPayment.setOplaty_Cena(p.getPrimitivePropertyAsString(tableName+"Cena"));
			tmpPayment.setOplaty_Data(p.getPrimitivePropertyAsString(tableName+"Data"));
			tmpPayment.setOplaty_ID(Integer.parseInt(p.getPrimitivePropertyAsString(tableName+"ID")));
			tmpPayment.setOplaty_Kurs(p.getPrimitivePropertyAsString(tableName+"Kurs"));
			tmpPayment.setOplaty_Miesiac(p.getPrimitivePropertyAsString(tableName+"Miesiac"));
			tmpPayment.setOplaty_Rok(p.getPrimitivePropertyAsString(tableName+"Rok"));
			tmpPayment.setOplaty_Typ(p.getPrimitivePropertyAsString(tableName+"Typ"));
			tmpPayment.setOplaty_WazneDo(p.getPrimitivePropertyAsString(tableName+"WazneDo"));
			tmpPayment.setOplaty_WazneOd(p.getPrimitivePropertyAsString(tableName+"WazneOd"));
						
			payments.add(tmpPayment);
		}
		
		return payments;
	}
	
	/***
	 * Pobiera liste powiadomien
	 * 
	 * @return ArrayList<Powiadomienie> Lista powiadomien
	 * @throws Exception
	 */
	public ArrayList<Powiadomienie> getNotifications() throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Powiadomienia_";
		
		ArrayList<Powiadomienie> notifications = new ArrayList<Powiadomienie>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, null);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject n = (SoapObject) response.getProperty(i);
			
			Powiadomienie tmpNotify = new Powiadomienie();
			
			tmpNotify.setPowiadomienia_ID(Integer.parseInt(n.getPrimitivePropertyAsString(tableName+"ID")));
			tmpNotify.setPowiadomienia_Tresc(n.getPrimitivePropertyAsString(tableName+"Tresc"));
			tmpNotify.setPowiadomienia_Tytul(n.getPrimitivePropertyAsString(tableName+"Tytul"));
			tmpNotify.setPracownik_ID(Integer.parseInt(n.getPrimitivePropertyAsString("Pracownik_ID")));
						
			notifications.add(tmpNotify);
		}
		
		return notifications;
	}
	
	/***
	 * Pobiera liste pracownikow obiektu
	 * 
	 * @return ArrayList<Pracownik> Lista pracownikow
	 * @throws Exception
	 */
	public ArrayList<Pracownik> getWorkers() throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Pracownicy_";
		
		ArrayList<Pracownik> workers = new ArrayList<Pracownik>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, null);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject w = (SoapObject) response.getProperty(i);
			
			Pracownik tmpWorker = new Pracownik();
			
			tmpWorker.setPracownicy_Aktywni(w.getPrimitivePropertyAsString(tableName+"Aktywni"));
			tmpWorker.setPracownicy_ID(Integer.parseInt(w.getPrimitivePropertyAsString(tableName+"ID")));
			tmpWorker.setPracownicy_Imie(w.getPrimitivePropertyAsString(tableName+"Imie"));
			tmpWorker.setPracownicy_Kod(w.getPrimitivePropertyAsString(tableName+"Kod"));
			tmpWorker.setPracownicy_Mail(w.getPrimitivePropertyAsString(tableName+"Mail"));
			tmpWorker.setPracownicy_Miasto(w.getPrimitivePropertyAsString(tableName+"Miasto"));
			tmpWorker.setPracownicy_Nazwisko(w.getPrimitivePropertyAsString(tableName+"Nazwisko"));
			tmpWorker.setPracownicy_NIP(w.getPrimitivePropertyAsString(tableName+"NIP"));
			tmpWorker.setPracownicy_Numer_domu(w.getPrimitivePropertyAsString(tableName+"Numer_domu"));
			tmpWorker.setPracownicy_Pesel(w.getPrimitivePropertyAsString(tableName+"Pesel"));
			tmpWorker.setPracownicy_Plec(w.getPrimitivePropertyAsString(tableName+"Plec"));
			tmpWorker.setPracownicy_Telefon(w.getPrimitivePropertyAsString(tableName+"Telefon"));
			tmpWorker.setPracownicy_Ulica(w.getPrimitivePropertyAsString(tableName+"Ulica"));
												
			workers.add(tmpWorker);
		}
		
		return workers;
	}
	
	/***
	 * Pobiera liste wydarzen
	 * 
	 * Wydarzenie moze nie miec przypisanej grupy, w takim przypadku jest to wartosc 0
	 * 
	 * @return ArrayList<Wydarzenie> Lista wydarzen
	 * @throws Exception
	 */
	public ArrayList<Wydarzenie> getEvents() throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Wydarzenia_";
		
		ArrayList<Wydarzenie> events = new ArrayList<Wydarzenie>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, null);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject e = (SoapObject) response.getProperty(i);
			
			Wydarzenie tmpEvent = new Wydarzenie();
			
			tmpEvent.setPracownik_ID(Integer.parseInt(e.getPrimitivePropertyAsString("Pracownik_ID")));
			tmpEvent.setWydarzenia_ID(Integer.parseInt(e.getPrimitivePropertyAsString(tableName+"ID")));
			tmpEvent.setWydarzenia_Kolor(e.getPrimitivePropertyAsString(tableName+"Kolor"));
			tmpEvent.setWydarzenia_KoniecDate(e.getPrimitivePropertyAsString(tableName+"KoniecDate"));
			tmpEvent.setWydarzenia_Notka(e.getPrimitivePropertyAsString(tableName+"Notka"));
			tmpEvent.setWydarzenia_StartDate(e.getPrimitivePropertyAsString(tableName+"StartDate"));
			tmpEvent.setWydarzenia_Tytul(e.getPrimitivePropertyAsString(tableName+"Tytul"));
			tmpEvent.setGrupa_ID(Integer.parseInt(e.getPrimitivePropertyAsString("Grupy_ID")));
			
			events.add(tmpEvent);
		}
		
		return events;
	}
	
	/***
	 * Pobiera liste wydarzen dla konkretnego pracownika
	 * 
	 * @param workerId Id pracownika
	 * @return ArrayList<Wydarzenie> Lista wydarzen
	 * @throws Exception
	 */
	public ArrayList<Wydarzenie> getEventsForUser(int workerId) throws Exception{		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		String tableName = "Wydarzenia_";
		
		Map<Integer, Object[]> params = new HashMap();
		
		params.put(1, new Object[]{"workerId", workerId});
		
		ArrayList<Wydarzenie> events = new ArrayList<Wydarzenie>();
		
		SoapObject response = (SoapObject) SFSSoapClient.getInstace().soapCall(methodName, params);
		
		for (int i = 0; i < response.getPropertyCount(); i++) {
			SoapObject e = (SoapObject) response.getProperty(i);
			
			Wydarzenie tmpEvent = new Wydarzenie();
			
			tmpEvent.setPracownik_ID(Integer.parseInt(e.getPrimitivePropertyAsString("Pracownik_ID")));
			tmpEvent.setWydarzenia_ID(Integer.parseInt(e.getPrimitivePropertyAsString(tableName+"ID")));
			tmpEvent.setWydarzenia_Kolor(e.getPrimitivePropertyAsString(tableName+"Kolor"));
			tmpEvent.setWydarzenia_KoniecDate(e.getPrimitivePropertyAsString(tableName+"KoniecDate"));
			tmpEvent.setWydarzenia_Notka(e.getPrimitivePropertyAsString(tableName+"Notka"));
			tmpEvent.setWydarzenia_StartDate(e.getPrimitivePropertyAsString(tableName+"StartDate"));
			tmpEvent.setWydarzenia_Tytul(e.getPrimitivePropertyAsString(tableName+"Tytul"));
			tmpEvent.setGrupa_ID(Integer.parseInt(e.getPrimitivePropertyAsString("Grupy_ID")));
												
			events.add(tmpEvent);
		}
		
		return events;
	}
	
	/***
	 * Dodaje powiadomienie do bazy
	 * 
	 * @param title Tytul wiadomosci
	 * @param text Tresc wiadomosci
	 * @param pracownikId Id pracownika
	 * @return true Informacja czy udalo sie dodac powiadomienie 
	 * @throws Exception
	 */
	public boolean addNotification(String title, String text, int pracownikId) throws Exception{
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		
		Map<Integer, Object[]> params = new HashMap();
		
		params.put(1, new String[]{"title", title});
		params.put(2, new String[]{"text", text});
		params.put(3, new String[]{"pracownikId", Integer.toString(pracownikId)});
	
		SoapPrimitive response = (SoapPrimitive) SFSSoapClient.getInstace().soapCall(methodName, params);
		
		boolean result = Boolean.valueOf(response.toString());
		
		return result;
	}
	
	/***
	 * Uwierzytelnianie uzytkownika systemu 
	 * @param login Nazwa uzytkownika
	 * @param password Haslo uzytkownika
	 * @return true Jesli uwierzytelniono, false jesli nie
	 * @throws Exception
	 */
	public boolean verifyUser(String login, String password) throws Exception{
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		
		Map<Integer, Object[]> params = new HashMap();
		
		params.put(1, new String[]{"login", login});
		params.put(2, new String[]{"password", password});
	
		SoapPrimitive response = (SoapPrimitive) SFSSoapClient.getInstace().soapCall(methodName, params);
		
		boolean result = Boolean.valueOf(response.toString());
		
		return result;
	}
	
	/***
	 * Pobranie id pracownika do ktorego nalezy konto systemowe
	 * 
	 * @param login Nazwa uzytkownika za pomoca ktorej loguje sie do systemu
	 * @return int Id pracownika w bazie pracownikow, lub 0 jesli konto jest niepowiazane z pracownikiem
	 * @throws Exception
	 */

	public int getAssignWorker(String login) throws Exception{
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		
		Map<Integer, Object[]> params = new HashMap();
		
		params.put(1, new String[]{"login", login});
	
		SoapPrimitive response = (SoapPrimitive) SFSSoapClient.getInstace().soapCall(methodName, params);
		
		int result = Integer.valueOf(response.toString());
		
		return result;
	}
	
	/***
	 * Dodanie lub uaktualnienie listy obecnosci na zajeciach
	 * 
	 * @param presenceId ID listy obecnosci w bazie 0 - dla nowej
	 * @param eventId ID wydarzenia powiazanego z lista
	 * @param workerId ID pracownika sprawdzajacego obecnosc
	 * @param presenceList Lista obecnosci w postaci mapy k=ID klienta, v=true|false
	 * @return boolean true jesli udalo sie zapisac
	 * @throws Exception
	 */
	public boolean checkPresence(int presenceId, int eventId, int workerId, Map<Integer, Boolean> presenceList) throws Exception{
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		
		String list="";
		
		Set<Entry<Integer, Boolean>> entrySet = presenceList.entrySet();
		
		for (Iterator<Entry<Integer, Boolean>> iterator = entrySet.iterator(); iterator.hasNext();) {
			Entry<Integer, Boolean> entry = (Entry<Integer, Boolean>) iterator
					.next();
			
			
			list = list.concat(entry.getKey().toString());
			list = list.concat("=");
			list = list.concat((entry.getValue()) ? "T" : "N");
			list = list.concat(";");
			
		}
		
		SortedMap<Integer, Object[]> params = new TreeMap<Integer, Object[]>();
		
		params.put(1, new Object[]{"presenceId", (presenceId==0) ? 0 : presenceId});
		params.put(2, new Object[]{"eventId", eventId});
		params.put(3, new Object[]{"workerId", workerId});
		params.put(4, new Object[]{"presenceList", list});
	
		
		SoapPrimitive response = (SoapPrimitive) SFSSoapClient.getInstace().soapCall(methodName, params);
		
		boolean result = Boolean.valueOf(response.toString());
		
		return result;
	}
	
	
	
	

}
