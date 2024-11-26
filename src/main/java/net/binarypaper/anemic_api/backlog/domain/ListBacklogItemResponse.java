package net.binarypaper.anemic_api.backlog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;

public record ListBacklogItemResponse(@JsonIgnore UUID backlogItemId, String name) {}
