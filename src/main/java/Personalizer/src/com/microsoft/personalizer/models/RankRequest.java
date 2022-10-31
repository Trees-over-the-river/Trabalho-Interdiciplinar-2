/**
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package Personalizer.src.com.microsoft.personalizer.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request a set of actions to be ranked by the personalization service.
 */
public class RankRequest {
    /**
     * Features of the context used for personalization as a
     * dictionary of dictionaries. This depends on the application, and
     * typically includes features about the current user, their
     * device, profile information, data about time and date, etc.
     */
    @JsonProperty(value = "contextFeatures")
    private List<Object> contextFeatures;

    /**
     * The set of actions the Personalization Service can pick from.
     * The set should not contain more than 50 actions.
     * The order of the actions does not affect the rank result but the order
     * should match the sequence your application would have used to display
     * them.
     */
    @JsonProperty(value = "actions", required = true)
    private List<RankableAction> actions;

    /**
     * The set of action ids to exclude from ranking.
     */
    @JsonProperty(value = "excludedActions")
    private List<String> excludedActions;

    /**
     * Optionally pass an eventId that uniquely identifies this Rank event.
     * If null, the service generates a unique eventId. The eventId will be
     * used for
     * associating this request with its reward, as well as seeding the
     * pseudo-random
     * generator when making a personalization call.
     */
    @JsonProperty(value = "eventId")
    private String eventId;

    /**
     * Send false if the user will see the rank results, therefore
     * Personalization will expect a Reward call, otherwise it will assign the
     * default
     * Reward to the event. Send true if it is possible the user will not see
     * the
     * rank results, because the page is rendering later, or the Rank results
     * may be
     * overriden by code further downstream.
     */
    @JsonProperty(value = "deferActivation")
    private Boolean deferActivation;

    /**
     * Get features of the context used for personalization as a
     dictionary of dictionaries. This depends on the application, and
     typically includes features about the current user, their
     device, profile information, data about time and date, etc.
     *
     * @return the contextFeatures value
     */
    public List<Object> contextFeatures() {
        return this.contextFeatures;
    }

    /**
     * Set features of the context used for personalization as a
     dictionary of dictionaries. This depends on the application, and
     typically includes features about the current user, their
     device, profile information, data about time and date, etc.
     *
     * @param contextFeatures the contextFeatures value to set
     * @return the RankRequest object itself.
     */
    public RankRequest withContextFeatures(List<Object> contextFeatures) {
        this.contextFeatures = contextFeatures;
        return this;
    }

    /**
     * Get the set of actions the Personalization Service can pick from.
     The set should not contain more than 50 actions.
     The order of the actions does not affect the rank result but the order
     should match the sequence your application would have used to display them.
     *
     * @return the actions value
     */
    public List<RankableAction> actions() {
        return this.actions;
    }

    /**
     * Set the set of actions the Personalization Service can pick from.
     The set should not contain more than 50 actions.
     The order of the actions does not affect the rank result but the order
     should match the sequence your application would have used to display them.
     *
     * @param actions the actions value to set
     * @return the RankRequest object itself.
     */
    public RankRequest withActions(List<RankableAction> actions) {
        this.actions = actions;
        return this;
    }

    /**
     * Get the set of action ids to exclude from ranking.
     *
     * @return the excludedActions value
     */
    public List<String> excludedActions() {
        return this.excludedActions;
    }

    /**
     * Set the set of action ids to exclude from ranking.
     *
     * @param excludedActions the excludedActions value to set
     * @return the RankRequest object itself.
     */
    public RankRequest withExcludedActions(List<String> excludedActions) {
        this.excludedActions = excludedActions;
        return this;
    }

    /**
     * Get optionally pass an eventId that uniquely identifies this Rank event.
     If null, the service generates a unique eventId. The eventId will be used for
     associating this request with its reward, as well as seeding the pseudo-random
     generator when making a personalization call.
     *
     * @return the eventId value
     */
    public String eventId() {
        return this.eventId;
    }

    /**
     * Set optionally pass an eventId that uniquely identifies this Rank event.
     If null, the service generates a unique eventId. The eventId will be used for
     associating this request with its reward, as well as seeding the pseudo-random
     generator when making a personalization call.
     *
     * @param eventId the eventId value to set
     * @return the RankRequest object itself.
     */
    public RankRequest withEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    /**
     * Get send false if the user will see the rank results, therefore
     Personalization will expect a Reward call, otherwise it will assign the default
     Reward to the event. Send true if it is possible the user will not see the
     rank results, because the page is rendering later, or the Rank results may be
     overriden by code further downstream.
     *
     * @return the deferActivation value
     */
    public Boolean deferActivation() {
        return this.deferActivation;
    }

    /**
     * Set send false if the user will see the rank results, therefore
     Personalization will expect a Reward call, otherwise it will assign the default
     Reward to the event. Send true if it is possible the user will not see the
     rank results, because the page is rendering later, or the Rank results may be
     overriden by code further downstream.
     *
     * @param deferActivation the deferActivation value to set
     * @return the RankRequest object itself.
     */
    public RankRequest withDeferActivation(Boolean deferActivation) {
        this.deferActivation = deferActivation;
        return this;
    }

}
