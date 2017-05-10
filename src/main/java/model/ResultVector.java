package model;

import java.util.ArrayList;
import java.util.List;

public class ResultVector {

	private List<Double> result;

	public ResultVector(List<Double> result) {
		this.result = result;
	}

	public void setResult(List<Double> result) {
		this.result = result;
	}

	public List<Double> getResult() {
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.size(); i++) {
			sb.append("x" + (i+1) + " = " + result.get(i)+"\n");
		}
		return sb.toString();
	}
}
