package com.ragenotes.blacklist.service;

import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.profiles.AcceptorProfile;
import com.ragenotes.blacklist.entity.profiles.ReviewerProfile;
import com.ragenotes.blacklist.entity.profiles.VoterProfile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service(CodeGeneratorService.NAME)
public class CodeGeneratorServiceBean implements CodeGeneratorService {

    @Override
    public String generateEntryCode(BlackListEntry entry) {
        return aggregateStrings(entry.getFirstName(), entry.getSecondName(), entry.getLastName(),
                entry.getFullName(), entry.getNickName());
    }

    @Override
    public String generateReviewerCode(ReviewerProfile profile) {
        return aggregateStrings(profile.getName(), profile.getUser().getFirstName(),
                profile.getUser().getLastName());
    }

    @Override
    public String generateVoterCode(VoterProfile profile) {
        return aggregateStrings(profile.getName(), profile.getUser().getFirstName(),
                profile.getUser().getLastName());
    }

    @Override
    public String generateAcceptorCode(AcceptorProfile profile) {
        return aggregateStrings(profile.getName(), profile.getUser().getFirstName(),
                profile.getUser().getLastName());
    }

    private String aggregateStrings(String... strings) {
        String aggregation = StringUtils.join(strings, "_");

        if (aggregation.length() > 100) {
            aggregation = codingAggregateStrings(strings);

            if (aggregation.length() > 100) {
                aggregation = hashingAggregateStrings(strings);
            }
        }

        return aggregation;
    }

    private String codingAggregateStrings(String... strings) {
        List<String> stringList = getStringStarts(strings);

        stringList.add(getStringsHashSum(strings));

        return StringUtils.join(stringList.toArray(new String[0]), "_");
    }

    private String hashingAggregateStrings(String... strings) {
        List<String> stringList = new ArrayList<>();

        stringList.add("CODE");
        stringList.add(getStringsHashSum(strings));
        stringList.add(getStringsHashSum(getStringStarts(strings).toArray(new String[0])));

        return StringUtils.join(stringList.toArray(new String[0]), "_");
    }

    private String getStringsHashSum(String... strings) {
        Long hashSum = 0L;
        for (String string : strings) {
            hashSum += string.hashCode();
        }
        return Long.toHexString(hashSum);
    }

    private List<String> getStringStarts(String... strings) {
        return Arrays.stream(strings)
                .map(str -> str.substring(0, 2))
                .collect(Collectors.toList());
    }

}