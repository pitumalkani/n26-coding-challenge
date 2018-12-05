package com.n26.entity.response;

import java.math.BigDecimal;

/**
 * Instantiates a new statistic.
 */
public class Statistic {

    /** The sum. */
    private BigDecimal sum = format(BigDecimal.ZERO);

    /** The avg. */
    private BigDecimal avg = format(BigDecimal.ZERO);

    /** The max. */
    private BigDecimal max = format(BigDecimal.ZERO);

    /** The min. */
    private BigDecimal min = format(BigDecimal.ZERO);

    /** The count. */
    private Long count = 0l;

    public void setSum( BigDecimal sum ) {
        this.sum = format( sum );
    }

    public void setAvg( BigDecimal avg ) {
        this.avg = format( avg );
    }

    public void setMax( BigDecimal max ) {
        this.max = format( max );
    }

    public void setMin( BigDecimal min ) {
        this.min = format( min );
    }

    public Long getCount() {
        return count;
    }

    public void setCount( Long count ) {
        this.count = count;
    }

    public String getSum() {
        return sum.toString();
    }

    public String getAvg() {
        return avg.toString();
    }

    public String getMax() {
        return max.toString();
    }

    public String getMin() {
        return min.toString();
    }


    /**
     * Format.
     *
     * @param value the value
     * @return the big decimal
     */
    public static BigDecimal format( BigDecimal value ) {
        value = value.setScale( 2, BigDecimal.ROUND_HALF_UP );
        return value;
    }

}
