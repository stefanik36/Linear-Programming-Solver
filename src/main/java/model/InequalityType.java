package model;

public enum InequalityType {
	SMALLER {
		public String toString() {
			return "<";
		}
	},
	EQUAL {
		public String toString() {
			return "=";
		}
	},
	BIGGER {
		public String toString() {
			return ">";
		}
	};
}
