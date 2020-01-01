﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Models.Domain.Repositories {
    public interface IStudieTaskRepository : IBaseRepository<StudieTask> {
        void update( StudieTask task);
    }
}
