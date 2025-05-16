package com.novice.debatenovice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.novice.debatenovice.enums.USER_EXPERIENCE;
import com.novice.debatenovice.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("averageScore")
    private Double averageScore;

    @JsonProperty("username")
    private String username;

    @JsonProperty("roles")
    private Set<USER_ROLE> roles;

    @JsonProperty("email")
    private String email;

    @JsonProperty("experience")
    private USER_EXPERIENCE experience;

//    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonProperty("createdAt")
//    private LocalDateTime createdAt;

    @JsonProperty("password")
    private String password;

//    public static class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
//        @Override
//        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//            if (p.isExpectedStartArrayToken()) {
//                // Assuming the input is an array of integers [year, month, day, hour, minute, second, nanosecond]
//                int[] dateTimeArray = p.readValueAs(int[].class);
//                return LocalDateTime.of(
//                        dateTimeArray[0],  // year
//                        dateTimeArray[1],  // month
//                        dateTimeArray[2],  // day
//                        dateTimeArray[3],  // hour
//                        dateTimeArray[4],  // minute
//                        dateTimeArray[5],  // second
//                        dateTimeArray[6]   // nanosecond
//                );
//            }
//            // Fallback to standard parsing if not an array
//            return LocalDateTime.parse(p.getValueAsString());
//        }
//    }

}
