package algo.winddata;

import java.time.LocalDate;
import java.time.LocalTime;

public class WindMeasurement {

        private LocalDate date;
        private LocalTime time;
        private double windDirection;
        private String windDirectionQuality;
        private double windSpeed;
        private String windSpeedQuality;

        public WindMeasurement(LocalDate date, LocalTime time, double windDirection, String windDirectionQuality, double windSpeed,
                                                               String windSpeedQuality) {



            this.date = date;
            this.time = time;
            this.windDirection = windDirection;
            this.windDirectionQuality = windDirectionQuality;
            this.windSpeed = windSpeed;
            this.windSpeedQuality = windSpeedQuality;

        }

    public double getWindSpeed() {
        return windSpeed;
    }

    public LocalTime getTime() {
        return time;
    }
    public String getWindDirectionQuality() {
        return windDirectionQuality;
    }

    public String getWindSpeedQuality() {
        return windSpeedQuality;
    }


}
