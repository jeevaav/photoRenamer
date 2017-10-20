package photo_renamer;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A time stamp.
 * 
 * @author Jeevaa
 * @author Tiansheng
 *
 */
public class TimeStamp implements Serializable {

	/** The serial ID of this TimeStamp. */
	private static final long serialVersionUID = -697972516511934072L;

	/** The date of this TimeStamp. */
	private int date;

	/** The year of this TimeStamp. */
	private int year;

	/** The month of this TimeStamp. */
	private int month;

	/** The hour of this TimeStamp. */
	private int hour;

	/** The minute of this TimeStamp. */
	private int minute;

	/** The second of this TimeStamp. */
	private int second;

	/**
	 * Creates a new TimeStamp with the given year, month, date, hour, minute
	 * and second.
	 * 
	 * @param year
	 *            the year of the TimeStamp
	 * @param month
	 *            the month of the TimeStamp
	 * @param date
	 *            the date of the TimeStamp
	 * @param hour
	 *            the hour of the TimeStamp
	 * @param minute
	 *            the minute of the TimeStamp
	 * @param second
	 *            the second of the TimeStamp
	 */
	public TimeStamp(int year, int month, int date, int hour, int minute, int second) {

		this.date = date;
		this.year = year;
		this.month = month;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	/**
	 * Returns the string representation of this TimeStamp.
	 * 
	 * @return the string representation of this TimeStamp
	 */
	@Override
	public String toString() {
		String currDate = this.year + "/" + this.month + "/" + this.date;
		String currTime = this.hour + ":" + this.minute + ":" + this.second;
		return currDate + "--" + currTime;

	}

	/**
	 * Returns the HashCode of this TimeStamp.
	 * 
	 * @return the HashCode of this TimeStamp
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + date;
		result = prime * result + hour;
		result = prime * result + minute;
		result = prime * result + month;
		result = prime * result + second;
		result = prime * result + year;
		return result;
	}

	// Equals method written for comparison of keys in the record of Image class

	/**
	 * Returns true if this TimeStamp is equals to other TimeStamp.
	 * 
	 * @return whether this TimeStamp equals other TimeStamp
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimeStamp other = (TimeStamp) obj;
		if (date != other.date)
			return false;
		if (hour != other.hour)
			return false;
		if (minute != other.minute)
			return false;
		if (month != other.month)
			return false;
		if (second != other.second)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	/**
	 * Returns a newly created TimeStamp based on the given time in the format:
	 * "year/month/date--hour:minute:second".
	 * 
	 * @return TimeStamp based on the given time.
	 */
	public static TimeStamp getTimeStamp(String time) {
		String[] timeArray = time.split("--");
		String[] dayMonthYear = timeArray[0].split("/");
		String[] secMinHour = timeArray[1].split(":");
		TimeStamp newTime = new TimeStamp(Integer.parseInt(dayMonthYear[0]), Integer.parseInt(dayMonthYear[1]),
				Integer.parseInt(dayMonthYear[2]), Integer.parseInt(secMinHour[0]), Integer.parseInt(secMinHour[1]),
				Integer.parseInt(secMinHour[2]));
		return newTime;
	}

	public static void main(String[] args) {
		/*
		 * ZonedDateTime zdt = ZonedDateTime.now(); TimeStamp time = new
		 * TimeStamp(zdt.getYear(), zdt.getMonthValue(), zdt.getDayOfMonth(),
		 * zdt.getHour(), zdt.getMinute(), zdt.getSecond()); TimeStamp newTime =
		 * new TimeStamp(zdt.getYear(), zdt.getMonthValue(),
		 * zdt.getDayOfMonth(), zdt.getHour(), zdt.getMinute(),
		 * zdt.getSecond());
		 */
	}

}
