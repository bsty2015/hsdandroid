package com.jc.pay;

import com.jc.bank.Card;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lrh on 10/9/15.
 */
public class ProductPayInfo {

    private BigDecimal remainAmt;

    private BigDecimal investAmt;

    private BigDecimal profit;

    private Product product;

    private Card card;

    private List<Card> ownCards;


    public BigDecimal getRemainAmt() {
        return remainAmt;
    }

    public void setRemainAmt(BigDecimal remainAmt) {
        this.remainAmt = remainAmt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Card> getOwnCards() {
        return ownCards;
    }

    public void setOwnCards(List<Card> ownCards) {
        this.ownCards = ownCards;
    }

    public BigDecimal getInvestAmt() {
        return investAmt;
    }

    public void setInvestAmt(BigDecimal investAmt) {
        this.investAmt = investAmt;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public static class Product{

        private Integer id;

        private String name;

        private Integer duration;

        private BigDecimal rate;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public void setRate(BigDecimal rate) {
            this.rate = rate;
        }
    }
}
