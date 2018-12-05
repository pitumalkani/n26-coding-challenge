package com.n26.entity.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * Instantiates a new transaction.
 */
@Data
public class Transaction {

	/** The amount. */
	@NotNull
	@Min(0)
	private BigDecimal amount;

	/** The timestamp. */
	@NotNull
	private LocalDateTime timestamp;
	
}
