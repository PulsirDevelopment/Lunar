package net.pulsir.lunar.utils.time;

import net.pulsir.lunar.Lunar;

public class Time {

    public static String parse(long l) {
        String seconds = Lunar.getInstance().getLanguage().getConfiguration().getString("SESSION.SECONDS");
        String minutes = Lunar.getInstance().getLanguage().getConfiguration().getString("SESSION.MINUTES");
        String hours = Lunar.getInstance().getLanguage().getConfiguration().getString("SESSION.HOURS");
        String days = Lunar.getInstance().getLanguage().getConfiguration().getString("SESSION.DAYS");

        if (l < 60) {
            return l + " " + seconds;
        } else if (l > 60 && l < 3600) {
            String minutesCount = String.valueOf(((l % 86400) % 3600) / 60);
            String secondsCount = String.valueOf(((l % 86400) % 3600) % 60);

            if (Integer.parseInt(secondsCount) < 10) {
                secondsCount = "0" + secondsCount;
            }

            if (Integer.parseInt(minutesCount) < 10) {
                minutesCount = "0" + minutesCount;
            }

            return minutesCount + ":" + secondsCount + " " + minutes;
        } else if (l > 3600 && l < 86400) {
            String hoursCount = String.valueOf((l % 86400) / 3600);
            String minutesCount = String.valueOf(((l % 86400) % 3600) / 60);
            String secondsCount = String.valueOf(((l % 86400) % 3600) % 60);

            if (Integer.parseInt(secondsCount) < 10) {
                secondsCount = "0" + secondsCount;
            }

            if (Integer.parseInt(minutesCount) < 10) {
                minutesCount = "0" + minutesCount;
            }

            if (Integer.parseInt(hoursCount) < 10) {
                hoursCount = "0" + hoursCount;
            }

            return hoursCount + ":" + minutesCount + ":" + secondsCount + " " + hours;
        } else {
            String daysCount = String.valueOf(l / 86400);
            String hoursCount = String.valueOf((l % 86400) / 3600);
            String minutesCount = String.valueOf(((l % 86400) % 3600) / 60);
            String secondsCount = String.valueOf(((l % 86400) % 3600) % 60);

            if (Integer.parseInt(secondsCount) < 10) {
                secondsCount = "0" + secondsCount;
            }

            if (Integer.parseInt(minutesCount) < 10) {
                minutesCount = "0" + minutesCount;
            }

            if (Integer.parseInt(hoursCount) < 10) {
                hoursCount = "0" + hoursCount;
            }

            if (Integer.parseInt(daysCount) < 10) {
                daysCount = "0" + daysCount;
            }

            return daysCount + ":" + hoursCount + ":" + minutesCount + ":" + secondsCount + " " + days;
        }
    }
}
