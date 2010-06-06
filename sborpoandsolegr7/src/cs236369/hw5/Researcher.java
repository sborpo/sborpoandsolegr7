package cs236369.hw5;

import java.awt.Image;
import java.util.HashSet;

public class Researcher extends User {

	private final int numberOfTimeSlotsInAYear = 34560;

	public Researcher(String login, String password, String name,
			String premissions, String group, String phoneNumber,
			String active, String address, Image photo) {
		super(login, password, name, premissions, group, phoneNumber, active,
				address, photo);
		// TODO Auto-generated constructor stub
	}

	public Researcher(String login) {
		super(login);
	}

	public enum Avilability {
		AVAILABLE(1), TAKEN(0), NOT_AVAILABLE(-1);

		private int actualValue;

		private Avilability(int value) {
			actualValue = value;
		}

		static public int compare(Avilability av1, Avilability av2) {
			if (av1.actualValue > av2.actualValue) {
				return 1;
			}
			if (av1.actualValue == av2.actualValue) {
				return 0;
			}
			return -1;
		}
	}

	public class AvailabilityResult {
		Avilability avilability;
		HashSet<Long> instruments = new HashSet<Long>();;

		/**
		 * @return the avilability
		 */
		public Avilability getAvilability() {
			return avilability;
		}

		/**
		 * @param avilability
		 *            the avilability to set
		 */
		public void setAvilability(Avilability avilability) {
			this.avilability = avilability;
		}

		/**
		 * @return the instruments
		 */
		public HashSet<Long> getInstruments() {
			return instruments;
		}

	}

	public AvailabilityResult[] Search(TimeSlot slot, int k, String type) {
		AvailabilityResult[] totalAvialableSlots = initializeTotalAvailableResults(numberOfTimeSlotsInAYear); // init
																												// to
																												// -1
		Avilability[] occupiedSlotsForCurrInstrument = null;
		Long currInstument = null;
		// TODO: get all current week's occupied slots

		mergeCurrInstrument(k, totalAvialableSlots,
				occupiedSlotsForCurrInstrument, currInstument);

		return null;
	}

	private void mergeCurrInstrument(int k,
			AvailabilityResult[] totalAvialableSlots,
			Avilability[] occupiedSlotsForCurrInstrument, Long currInstument) {
		int i = 0;
		for (int j = 0; j < occupiedSlotsForCurrInstrument.length; j++) {
			if (occupiedSlotsForCurrInstrument[j] == Avilability.NOT_AVAILABLE) {
				markAvialability(k, occupiedSlotsForCurrInstrument, i, j);
			}
		}
		for (int j = 0; j < totalAvialableSlots.length; j++) {
			if (Avilability.compare(totalAvialableSlots[j].getAvilability(),
					occupiedSlotsForCurrInstrument[j]) == 0
					&& !totalAvialableSlots[j].getAvilability().equals(
							Avilability.NOT_AVAILABLE)) {
				totalAvialableSlots[j].getInstruments().add(currInstument);
			}
			if (Avilability.compare(totalAvialableSlots[j].getAvilability(),
					occupiedSlotsForCurrInstrument[j]) == -1) {
				totalAvialableSlots[j].getInstruments().clear();
				totalAvialableSlots[j].getInstruments().add(currInstument);
			}

		}
	}

	private AvailabilityResult[] initializeTotalAvailableResults(
			int numberOfTimeSlotsInAYear) {
		AvailabilityResult[] temp = new AvailabilityResult[numberOfTimeSlotsInAYear];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = new AvailabilityResult();
			temp[i].setAvilability(Avilability.NOT_AVAILABLE);
		}
		return temp;
	}

	private void markAvialability(int k,
			Avilability[] occupiedSlotsForCurrInstrument, int i, int j) {
		while ((j - i >= k)) {
			if (occupiedSlotsForCurrInstrument[i] != Avilability.NOT_AVAILABLE) {
				occupiedSlotsForCurrInstrument[i] = Avilability.AVAILABLE;
			}
			i++;
		}
		while (j - i > 0) {
			if (occupiedSlotsForCurrInstrument[i] != Avilability.NOT_AVAILABLE) {
				occupiedSlotsForCurrInstrument[i] = Avilability.TAKEN;
			}
			i++;
		}
	}

}
