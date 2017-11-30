/*
 * Copyright (c) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package raspi_ui.backend.calendar;

import com.google.api.services.calendar.model.CalendarListEntry;
import raspi_ui.backend.calendar.json.CalenderInfo;
import raspi_ui.backend.calendar.json.Item;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Lists;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.Events;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yaniv Inbar
 */
public class CalendarData {
    private static final String APPLICATION_NAME = "Test for Tests";
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".store/calendar_sample");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static FileDataStoreFactory dataStoreFactory;
    private static HttpTransport httpTransport;
    private static com.google.api.services.calendar.Calendar client;

    static final java.util.List<com.google.api.services.calendar.model.Calendar> addedCalendarsUsingBatch = Lists.newArrayList();

    private static Credential authorize() throws Exception {
        // load client secrets
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(CalendarData.class.getResourceAsStream("/client_secrets.json")));
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println(
                    "Enter Client ID and Secret from https://code.google.com/apis/console/?api=calendar "
                            + "into calendar-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }
        // set up authorization code flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
                .build();
        // authorize
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static List<CalendarEvent> getEventsInTime(String begin, String end) throws IOException {

        main(null);

        List<CalendarEvent> events = new ArrayList<CalendarEvent>();

        CalendarList calendars = client.calendarList().list().execute();
        for (CalendarListEntry calendar : calendars.getItems()) {
            System.out.println("found calendar: " + calendar.getSummary() + " id: " + calendar.getId());

            if (calendar.getSummary().contains("inovex") || calendar.getSummary().contains("Feiertage")) {
                System.out.println("processing calendar: " + calendar.getSummary());

                String JSON_Events = client.events().list(calendar.getId()).setTimeMin(new DateTime(begin)).setTimeMax(new DateTime(end)).execute().toPrettyString();
                parseEvents(JSON_Events, events);
            }
        }

        System.out.println("found events: " + events.size());
        System.out.println("done getting calendar events");
        return events;

    }

    private static void parseEvents(String jsonString, List<CalendarEvent> addTo) {
        Gson gson = new Gson();

        System.out.println("parsing json...");

        CalenderInfo data = gson.fromJson(jsonString, CalenderInfo.class);
        System.out.println(data.getSummary() + " | " + data.getItems().size());

        for (Item item : data.getItems()) {
            try {
                CalendarEvent event = new CalendarEvent(item.getSummary(), item.getDescription(), item.getStart().getDateTime(), item.getEnd().getDateTime(), data, item);
                addTo.add(event);
                System.out.println(event);
                System.out.println();
            } catch (NullPointerException ex) {
                System.err.println("unable to get data for item: " + item.toString());
            }
        }

    }

    public static void main(String[] args) {
        try {
            // initialize the transport
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            // initialize the data store factory
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);

            // authorization
            Credential credential = authorize();

            // set up global CalendarData instance
            client = new com.google.api.services.calendar.Calendar.Builder(
                    httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();

            // run commands
            //showCalendars();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        //System.exit(1);
    }

    private static void showCalendars() throws IOException {
        CalendarList feed = client.calendarList().list().execute();

        System.out.println("main calendar list (json):");
        String JSON_Events = client.events().list("inovex.de_36373434353033313438@resource.calendar.google.com").setTimeMin(new DateTime("2017-10-14T13:15:03+00:00")).setTimeMax(DateTime.parseRfc3339("2017-12-01T00:00:00+00:00")).execute().toPrettyString();
        //System.out.println(JSON_Events);

        //parseJSON(JSON_Events);
    }

    private static com.google.api.services.calendar.model.Calendar updateCalendar(com.google.api.services.calendar.model.Calendar calendar) throws IOException {
        com.google.api.services.calendar.model.Calendar entry = new com.google.api.services.calendar.model.Calendar();
        entry.setSummary("Updated CalendarData for Testing");
        com.google.api.services.calendar.model.Calendar result = client.calendars().patch(calendar.getId(), entry).execute();
        return result;
    }

    /*private static void parseJSON(String jsonString) {
        Gson gson = new Gson();

        System.out.println("parsing json...");

        CalenderInfo data = gson.fromJson(jsonString, CalenderInfo.class);
        System.out.println(data.getSummary() + " | " + data.getItems().size());

        for (Item item : data.getItems()) {
            try {
                System.out.println("Item: " + item.getSummary());
                System.out.println("Date: " + item.getStart().getDateTime());
                System.out.println();
            } catch (NullPointerException ex) {
                System.err.println("unable to get data for item: " + item.toString());
            }
        }

    }*/

    private static void showEvents(com.google.api.services.calendar.model.Calendar calendar) throws IOException {
        Events feed = client.events().list(calendar.getId()).execute();
    }
}
