package model;

public enum ObjectiveType {
	MAXIMALIZE {
		public String toString() {
			return "MAXIMALIZE";
		}
	},
	MINIMALIZE {
		public String toString() {
			return "MINIMALIZE";
		}
	};
}
