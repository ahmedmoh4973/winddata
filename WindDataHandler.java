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

        for (String line : fileData) { // O(n)

            String[] parts = line.split(";"); // O(1)

            LocalDate date = LocalDate.parse(parts[0]); //  O(1)
            LocalTime time = LocalTime.parse(parts[1]); // O(1)
            double windDirection = Double.parseDouble(parts[2]); // O(1)
            String windDirectionQuality = parts[3]; //  O(1)
            double windSpeed = Double.parseDouble(parts[4]); //  O(1)
            String windSpeedQuality = parts[5]; //  O(1)

            WindMeasurement measurement = new WindMeasurement( date, time, windDirection, windDirectionQuality, windSpeed, windSpeedQuality); //  O(1)

            List<WindMeasurement> measurementsForDate = windData.get(date); //  O( log n)

            if (measurementsForDate == null) { //  O(1)

                measurementsForDate = new ArrayList<>(); //  O(1)

                windData.put(date, measurementsForDate); //  O(log n)
            }

            measurementsForDate.add(measurement); //  O(1)
        }

        System.out.println("Number of dates: " + windData.size());

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

		List<String> result = new ArrayList<>(); //  O(1)

		if(dateFrom.isAfter(dateTo)) { //  O(1)
			return result; //  O(1)
		}
        Map<LocalDate, List<WindMeasurement>> selectedData = windData.subMap(dateFrom, true, dateTo, true); //  O(log n)

        for (LocalDate date : selectedData.keySet()) { //  O(k)

            List<WindMeasurement> measurements = selectedData.get(date); //  O(1)

            double sum = 0; //  O(1)
            int count = 0; //  O(1)

            for (WindMeasurement measurement : measurements) { //  O(k)
                sum = sum + measurement.getWindSpeed(); //  O(1)
                count++; //  O(1)
            }

            if (count > 0) { //  O(1)
                double average = sum / count; //  O(1)
                result.add(date + " average wind speed: " + average + " m/s"); //  O(1)
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


		List<String> result = new ArrayList<>(); //  O(1)
		if (dateFrom.isAfter(dateTo)) { //  O(1)
			return result; //  O(1)
		}

		Map<LocalDate,List<WindMeasurement>> selectedData = windData.subMap(dateFrom, true, dateTo,true); //  O(log n)

		for (LocalDate date : selectedData.keySet()) { //  O(k)

			List<WindMeasurement> measurements = selectedData.get(date); //  O(1)

			int approvedCount = 0; //  O(1)
			int totalCount = 0; //  O(1)

			for (WindMeasurement measurement : measurements) { //  O(k)

			totalCount++; //  O(1)
			if(measurement.getWindDirectionQuality().equals("G")) { //  O(1)
				approvedCount++; //  O(1)
			}
			totalCount++; //  O(1)
			if (measurement.getWindSpeedQuality().equals("G")) { //  O(1)
				approvedCount++; //  O(1)
			}
			}
			if (totalCount > 0) { //  O(1)
				double percentage = ((double) approvedCount /totalCount ) * 100; //  O(1)

				result.add(date + ": " + percentage + " % approved values" ); //  O(1)

			}
		}

		return result;  //O(1)
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



        List<String> result = new ArrayList<>(); //  O(1)

        if (dateFrom.isAfter(dateTo)) { //  O(1)
            return result; //  O(1)
        }

        Map<LocalDate, List<WindMeasurement>> selectedData = //  O(log n)
                windData.subMap(dateFrom, true, dateTo, true); //  O(log n)

        for (LocalDate date : selectedData.keySet()) { //  O(k)

            List<WindMeasurement> measurements = selectedData.get(date); //  O(1)

            double highestSpeed = -1; //  O(1)
            LocalTime highestTime = null; //  O(1)

            for (WindMeasurement measurement : measurements) { //  O(k)

                if (measurement.getWindSpeed() > highestSpeed) { //  O(1)
                    highestSpeed = measurement.getWindSpeed(); //  O(1)
                    highestTime = measurement.getTime(); //  O(1)
                }
            }

            if (highestTime != null) { //  O(1)
                result.add(date + " " + highestTime + ": " + highestSpeed + " m/s"); //  O(1)
            }
        }
        return result; //O(1)
	}
}