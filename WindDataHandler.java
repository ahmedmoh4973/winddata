package algo.winddata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Retrieves wind data from a weather station file.
 */
public class WindDataHandler {

    private TreeMap<LocalDate, List<WindMeasurement>> windData = new TreeMap<>();



	/**
	 * Load wind data from file.
	 *
	 * @param filePath path to file with weather data
	 * @throws IOException if there is a problem while reading the file
	 */
	public void loadData(String filePath) throws IOException {
		List<String> fileData = Files.readAllLines(Paths.get(filePath));  //O(n)

        for (String line : fileData) {

            String[] parts = line.split(";");

            LocalDate date = LocalDate.parse(parts[0]);
            LocalTime time = LocalTime.parse(parts[1]);
            double windDirection = Double.parseDouble(parts[2]);
            String windDirectionQuality = parts[3];
            double windSpeed = Double.parseDouble(parts[4]);
            String windSpeedQuality = parts[5];

            WindMeasurement measurement = new WindMeasurement( date, time, windDirection, windDirectionQuality, windSpeed, windSpeedQuality);

            List<WindMeasurement> measurementsForDate = windData.get(date);

            if (measurementsForDate == null) {

                measurementsForDate = new ArrayList<>();

                windData.put(date, measurementsForDate);
            }

            measurementsForDate.add(measurement);
        }

        System.out.println("Number of dates: " + windData.size());

		//TODO: Structure data and put it in appropriate data structure
	}

	/**
	 * Search for average wind speed for dates. Result is sorted by date (ascending).
	 * When searching from 2000-01-01 to 2000-01-03 the result should be:
	 * 2000-01-01 average wind speed: 4.29 m/s
	 * 2000-01-02 average wind speed: 6.48 m/s
	 * 2000-01-03 average wind speed: 5.74 m/s
	 *
	 * @param dateFrom start date (YYYY-MM-DD) inclusive
	 * @param dateTo   end date (YYYY-MM-DD) inclusive
	 * @return average wind speed for each date, sorted by date
	 */
	public List<String> averageWindSpeed(LocalDate dateFrom, LocalDate dateTo) {

		//TODO: Implement method
		List<String> result = new ArrayList<>();

		if(dateFrom.isAfter(dateTo)) {
			return result;
		}
        Map<LocalDate, List<WindMeasurement>> selectedData = windData.subMap(dateFrom, true, dateTo, true);

        for (LocalDate date : selectedData.keySet()) {

            List<WindMeasurement> measurements = selectedData.get(date);

            double sum = 0;
            int count = 0;

            for (WindMeasurement measurement : measurements) {
                sum = sum + measurement.getWindSpeed();
                count++;
            }

            if (count > 0) {
                double average = sum / count;
                result.add(date + " average wind speed: " + average + " m/s");
            }
        }
		return result; //O(1)
	}

	/**
	 * Search for percentage of approved values (for both wind speed and wind direction) for dates.
	 * When searching from 2000-01-01 to 2000-01-03 the result should be:
	 * 2000-01-01: 33.33 % approved values
	 * 2000-01-02: 34.78 % approved values
	 * 2000-01-03: 34.78 % approved values
	 *
	 * @param dateFrom start date (YYYY-MM-DD) inclusive
	 * @param dateTo   end date (YYYY-MM-DD) inclusive
	 * @return approved values for each date, sorted by date
	 */
	public List<String> approvedValues(LocalDate dateFrom, LocalDate dateTo) {

		//TODO: Implement method
		return null;  //O(1)
	}

	/**
	 * Search for highest wind speed for dates.
	 * When searching from 2000-01-01 to 2000-01-03 the result should be:
	 * 2000-01-01 05:00: 5.0 m/s
	 * 2000-01-02 11:00: 9.0 m/s
	 * 2000-01-03 17:00: 9.0 m/s
	 *
	 * @param dateFrom start date (YYYY-MM-DD) inclusive
	 * @param dateTo   end date (YYYY-MM-DD) inclusive
	 * @return highest wind speed for each date, sorted by date
	 */
	public List<String> highestWindSpeed(LocalDate dateFrom, LocalDate dateTo) {

		//TODO: Implement method

        List<String> result = new ArrayList<>();

        if (dateFrom.isAfter(dateTo)) {
            return result;
        }

        Map<LocalDate, List<WindMeasurement>> selectedData =
                windData.subMap(dateFrom, true, dateTo, true);

        for (LocalDate date : selectedData.keySet()) {

            List<WindMeasurement> measurements = selectedData.get(date);

            double highestSpeed = -1;
            LocalTime highestTime = null;

            for (WindMeasurement measurement : measurements) {

                if (measurement.getWindSpeed() > highestSpeed) {
                    highestSpeed = measurement.getWindSpeed();
                    highestTime = measurement.getTime();
                }
            }

            if (highestTime != null) {
                result.add(date + " " + highestTime + ": "
                        + highestSpeed + " m/s");
            }
        }
        return result; //O(1)
	}
}