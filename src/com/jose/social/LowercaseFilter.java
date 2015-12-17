package com.jose.social;

import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class LowercaseFilter extends DocumentFilter {
	
	
	public void insertString(DocumentFilter.FilterBypass fb, int offset,
		      String text, javax.swing.text.AttributeSet attr) throws BadLocationException {
		    fb.insertString(offset, text.toLowerCase(), attr);
		  
	}
	
	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
		      String text, javax.swing.text.AttributeSet attr) throws BadLocationException {
		    fb.replace(offset, length, text.toLowerCase(), attr);
		  
	}
	
}
