package com.delirium.finapp.finance.domain;

import com.delirium.finapp.groups.domain.Group;
import com.delirium.finapp.images.FinImage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by morand3 on 12/23/2015.
 */
@Entity
@Table(name = "F_TRANSACTIONS")
public class Transaction {
    @Transient
    AccountRepository accountRepository;
    @Id
    @Column(name = "TRANSACTION_ID")
    @GeneratedValue
    @JsonView(TrasactionView.class)
    private Long id;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private Group group;
    @Column
    @JsonView(TrasactionView.class)
    private String title;
    @Column
    @JsonView(TrasactionView.class)
    private String target;
    @Column
    @JsonView(TrasactionView.class)
    private Double amount;
    @Column
    @JsonView(TrasactionView.class)
    private Date date;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    @JsonView(TrasactionView.class)
    private Category category;
    @Column
    @JsonView(TrasactionView.class)
    private String comment;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonView(TrasactionView.class)
    private Location location;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    @JsonView(TrasactionView.class)
    private Account account;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonView(TrasactionView.class)
    private FinImage image;
    @Transient
    private CategoryRepository categoryRepository;

    public Transaction() {
    }

    public Transaction(Group group, Double amount, Double longitude, Double latitude, Date date) {
        this.group = group;
        this.amount = amount;
        this.date = date;
        this.title = "TBD";
        this.location = new Location("TBD", latitude, longitude);
    }

    public Long getId() {
        return id;
    }

    public String getCategoryColor() {
        return (null == category) ? "black" : category.getColor();
    }

    @JsonView(TrasactionView.class)
    public Long getGroupId() {
        return (null == group) ? null : group.getId();
    }

    public void setGroup(Group group) {
        if (null == this.group || !this.group.equals(group)) {
            this.group = group;
        }
    }

    public void setRepositories(CategoryRepository categoryRepository, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    @JsonProperty("groupCategories")
    public List<Category> getCategories() {
        return (null == categoryRepository) ? null : categoryRepository.categoryForGroup(this.group);
    }

    @JsonProperty("groupAccounts")
    public List<Account> getAccounts() {
        return (null == accountRepository) ? null : accountRepository.accountsForGroup(this.group);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getAccount() {
        return (null == account) ? null : account.getId();
    }

    public void setAccount(Long account) {
//        this.account = findAccount;
    }

    public FinImage getImage() {
        return image;
    }

    public void setImage(FinImage image) {
        if (null == this.image || !this.image.equals(image)) {
            this.image = image;
        }
    }

    public void updateTransation(Transaction t) {
        if (t.getTitle() != null) {
            this.setTitle(t.getTitle());
        }
        if (t.getTarget() != null) {
            this.setTarget(t.getTarget());
        }
        if (t.getAmount() != null) {
            this.setAccount(t.getAccount());
        }
        if (t.getDate() != null) {
            this.setDate(t.getDate());
        }
        if (t.getCategory() != null) {
            this.setCategory(t.getCategory());
        }
        if (t.getComment() != null) {
            this.setComment(t.getComment());
        }
        if (t.getLocation() != null) {
            this.setLocation(t.getLocation());
        }
        if (t.account != null) {
            this.account = t.account;
        }
        if (t.getImage() != null) {
            this.setImage(t.getImage());
        }
    }

    public interface TrasactionView {
    }

    public interface TransactionWithExtraDetails extends TrasactionView {
    }
}
