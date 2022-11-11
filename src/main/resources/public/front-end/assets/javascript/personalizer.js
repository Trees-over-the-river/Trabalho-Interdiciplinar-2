// Copyright (c) Microsoft Corporation.
// Licensed under the MIT License.

/**
 * @summary Demonstrates the use of a Personalizer client to rank actions for multiple slots and reward the presented action.
 */
 import createPersonalizerClient from "@azure-rest/ai-personalizer";
import { isUnexpected } from "@azure-rest/ai-personalizer";

// Load the .env file if it exists
require("dotenv").config();





async function main() {
 const endpoint = process.env.PERSONALIZER_ENDPOINT;
 const key = process.env.PERSONALIZER_API_KEY;

 const client = createPersonalizerClient(endpoint, { key });

 // We want to rank the actions for two slots.
 const slots = [
   {
     id: "Categorias",
     baselineAction: "categories",
     features: [{ Size: "Large", Position: "Top Middle" }],
   },
 ];

 // The list of actions to be ranked with metadata associated for each action.
 const actions = [
   { id: "Categorias", features: [{ type: "int" }] },
 ];

 // The current context.
 const contextFeatures = [
   { User: { ProfileType: "AnonymousUser" } },
   { RecentActivity: { categories: getCategories() } },
 ];

 const request = {
   slots: slots,
   actions: actions,
   contextFeatures: contextFeatures,
 };

 log("Sending multi-slot rank request");
 const rankResponse = await client.path("/multislot/rank").post({ body: request });
 if (isUnexpected(rankResponse)) {
   throw rankResponse.body.error;
 }

 const rankOutput = rankResponse.body;
 const eventId = rankOutput.eventId;
 const slotResponses = rankOutput.slots;
 log(`Rank returned response with event id ${eventId} and recommended the following:`);
 for (const slotResponse of slotResponses) {
   log(`  Action ${slotResponse.rewardActionId} for slot ${slotResponse.id}`);
 }

 // The event response will be determined by how the user interacted with the action that was presented to them.
 // Let us say that they like the action presented to them for the Main Article slot and so we associate a reward of 1.
 log("Sending reward event for slot 1");

 const eventResponse = await client
   .path("/multislot/events/{eventId}/reward", eventId)
   .post({ body: { reward: [{ slotId: "Main Article", value: 1 }] } });
 if (isUnexpected(eventResponse)) {
   throw eventResponse.body.error;
 }

 log("Completed sending reward response");
}

function log(message) {
 console.log(message);
}

main().catch((err) => {
 console.error(
   `The sample encountered an error with code: ${err.code} and message: ${err.message}`
 );
});

async function getCategories() {
  return await fetch("/api/usuario/categorias/list")
}