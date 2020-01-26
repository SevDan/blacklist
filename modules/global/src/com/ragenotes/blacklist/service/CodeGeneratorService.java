package com.ragenotes.blacklist.service;

import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.profiles.AcceptorProfile;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.entity.profiles.VoterProfile;

public interface CodeGeneratorService {
    String NAME = "bl_CodeGeneratorService";

    String generateEntryCode(BlackListEntry entry);

    String generateReviewerCode(ReviewerProfile profile);

    String generateVoterCode(VoterProfile profile);

    String generateAcceptorCode(AcceptorProfile profile);
}