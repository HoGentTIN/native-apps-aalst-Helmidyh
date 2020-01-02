using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using StudyAPI.Models.Domain;

namespace StudyAPI.Data.Mapping {
    public class StudieHistoryConfiguration {
        public void Configure(EntityTypeBuilder<StudieVakHistory> builder) {
            builder.ToTable("StudieVakHistory");
            builder.HasKey(x => x.StudieVakHistoryId);

            builder.Property(x => x.StudieVakHistoryName).IsRequired();


        }
    }
}
