package core;import java.util.ArrayList;import java.util.HashMap;import java.util.Map;public class Player {    protected String color;    private int politicalScore;    private int religiousScore;    private int economicScore;    private int nbStupa;    private int nbDelegation;    private int nbTransactionDone;    private boolean completedOrder;    protected Village currentPosition;    private ArrayList<Resource> resources;    private ArrayList<Action> actions;    private ArrayList<Village> resTakenVillage;    private ArrayList<Village> delegationPutVillage;    private ArrayList<Village> barteringVillage;    private HashMap<Integer, Integer> delegations;    private int villageOrderId;    private int nbYacksOrder;    public Player(String color) {        this.color = color;        currentPosition = null;        politicalScore = 0;        religiousScore = 0;        economicScore = 0;        nbStupa = 5;        nbDelegation = 15;        completedOrder = false;        nbTransactionDone = 0;        resources = new ArrayList<>();        actions = new ArrayList<>();        resTakenVillage = new ArrayList<>();        delegationPutVillage = new ArrayList<>();        delegations = new HashMap<>();        barteringVillage = new ArrayList<>();    }    /**     * Constructeur par copie     *     * @param player     */    public Player(Player player) {        this.actions = new ArrayList<>();        this.resTakenVillage = new ArrayList<>();        this.delegationPutVillage = new ArrayList<>();        this.barteringVillage = new ArrayList<>();        this.color = player.color;        this.completedOrder = player.completedOrder;        this.delegations = new HashMap<>(player.delegations.size());        for (Map.Entry<Integer, Integer> entry : player.delegations.entrySet()) {            Integer regId = new Integer(entry.getKey());            Integer nbDelegations = entry.getValue();            this.delegations.put(regId, nbDelegations);        }        this.nbDelegation = player.nbDelegation;        this.nbStupa = player.nbStupa;        this.nbTransactionDone = 0;        this.nbYacksOrder = 0;        this.politicalScore = player.politicalScore;        this.economicScore = player.economicScore;        this.religiousScore = player.religiousScore;        this.villageOrderId = 0;        this.resources = new ArrayList<>();        for (Resource resource : player.resources) {            this.resources.add(new Resource(resource.getType()));        }    }    public void addResourceTaken(Village v) {        resTakenVillage.add(v);    }    public boolean canTakeResource() {        return (!resTakenVillage.contains(currentPosition));    }    public void addDelegationPut(Village v) {        delegationPutVillage.add(v);    }    public boolean canPutDelegation() {        return (completedOrder                && nbTransactionDone < 2                && nbDelegation > 0                && villageOrderId == currentPosition.getId()                && !delegationPutVillage.contains(currentPosition));    }    public boolean canBartering() {        return (completedOrder                && nbTransactionDone < 2                && villageOrderId == currentPosition.getId()                && !barteringVillage.contains(currentPosition));    }    public boolean canPutStupa() {        return (completedOrder                && nbTransactionDone < 2                && villageOrderId == currentPosition.getId()                && currentPosition.getStupa() == null                && nbStupa > 0);    }    public HashMap<Integer, Integer> getDelegations() {        return delegations;    }    public int getNbDelegation() {        return nbDelegation;    }    public void setPoliticalScore(int politicalScore) {        this.politicalScore = politicalScore;    }    public void setReligiousScore(int religiousScore) {        this.religiousScore = religiousScore;    }    public void setEconomicScore(int economicScore) {        this.economicScore = economicScore;    }    public void addDelegations(Region r, Integer nb) {        nbDelegation -= nb;        delegations.put(r.getId(), nb);        nbTransactionDone++;    }    public int getNbTransactionDone() {        return nbTransactionDone;    }    public int getNbStupa() {        return nbStupa;    }    public void putStupa() {        nbStupa--;        currentPosition.setStupa(this);        nbTransactionDone++;    }    public boolean hasCompletedOrder() {        return completedOrder;    }    public void setCompletedOrder(boolean completedOrder, int villageId, int nbYacksOrder) {        this.villageOrderId = villageId;        this.completedOrder = completedOrder;        this.nbYacksOrder = nbYacksOrder;    }    public int getVillageOrderId() {        return villageOrderId;    }    public int getNbYacksOrder() {        return nbYacksOrder;    }    public void addResource(Resource resource) {        this.resources.add(resource);    }    public void addAction(Action action) {        this.actions.add(action);    }    public int getPoliticalScore() {        return politicalScore;    }    public int getReligiousScore() {        return religiousScore;    }    public int getEconomicScore() {        return economicScore;    }    public String getColor() {        return color;    }    public Village getPosition() {        return currentPosition;    }    public void setPosition(Village v) {        currentPosition = v;    }    public Action getAction(int i) {        return actions.get(i);    }    public ArrayList<Resource> getResources() {        return resources;    }    public int getNbResources(Resource.Type type) {        int result = 0;        for (Resource resource : resources) {            if (resource.getType().equals(type)) {                result++;            }        }        return result;    }    public void removeResource(Resource r) {        resources.remove(r);    }    public Resource getSpecificResource(Resource.Type type) {        for (Resource res : resources) {            if (res.getType().equals(type)) {                return res;            }        }        return null;    }    public void clearEndOfTurn() {        actions.clear();        resTakenVillage.clear();        delegationPutVillage.clear();        barteringVillage.clear();        nbTransactionDone = 0;        setCompletedOrder(false, 0, 0);    }    public void setBoard(Board board) {    }    public int calculateInitPosition() {        return -1;    }    public void addBarteringVillage(Village position) {        barteringVillage.add(position);        nbTransactionDone++;    }}