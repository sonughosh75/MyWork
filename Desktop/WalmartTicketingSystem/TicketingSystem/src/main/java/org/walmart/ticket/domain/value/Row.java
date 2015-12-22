/**
 * 
 */
package org.walmart.ticket.domain.value;

/**
 * @author Sourav
 *
 */
public enum Row {
	A(1),
	B(2),
	C(3),
	D(4);
	
	private int value;
	
	Row(int rowNumber){
		this.value = rowNumber;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
