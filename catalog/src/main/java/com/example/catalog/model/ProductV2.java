package com.example.catalog.model;

import java.time.Instant;

// This is the v2 response shape. It is a separate class -- not a modified
// version of the v1 Product record. v1 and v2 coexist independently.
public record ProductV2(
        String id,
        String name,
        String productCategory,   // renamed from "category" -- this is the breaking change
        double price,
        Instant lastUpdated       // new field -- non-breaking for clients that ignore unknowns
) {}