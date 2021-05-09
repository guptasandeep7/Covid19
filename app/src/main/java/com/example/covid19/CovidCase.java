package com.example.covid19;

public class CovidCase{
//active confirmed deaths recovered state
    private long active;
    private long confirmed;
    private long deaths;
    private long recovered;
    private String stateName;

    public long getActive() {
        return active;
    }

    public long getConfirmed() {
        return confirmed;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public String getStateName() {
        return stateName;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public void setConfirmed(long confirmed) {
        this.confirmed = confirmed;
    }

    public void setDeaths(long deaths) {
        this.deaths = deaths;
    }

    public void setRecovered(long recovered) {
        this.recovered = recovered;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public CovidCase(long active,long confirmed,long deaths,long recovered,String stateName){
        setActive(active);
        setConfirmed(confirmed);
        setDeaths(deaths);
        setRecovered(recovered);
        setStateName(stateName);
    }
}
