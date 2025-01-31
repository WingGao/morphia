/*
 * Copyright (c) 2008-2016 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.morphia.test.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import org.bson.types.ObjectId;

import java.util.Objects;

@Entity
public class State {
    @Id
    public ObjectId id;
    @Property("state")
    public String state;
    @Property("biggestCity")
    public CityPopulation biggest;
    @Property("smallestCity")
    public CityPopulation smallest;

    public CityPopulation getBiggest() {
        return biggest;
    }

    public CityPopulation getSmallest() {
        return smallest;
    }

    public String getState() {
        return state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, biggest, smallest);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof State)) {
            return false;
        }
        final State state1 = (State) o;
        return Objects.equals(id, state1.id) &&
                Objects.equals(state, state1.state) &&
                Objects.equals(biggest, state1.biggest) &&
                Objects.equals(smallest, state1.smallest);
    }

    @Override
    public String toString() {
        return String.format("State{state='%s', biggest=%s, smallest=%s}", state, biggest, smallest);
    }
}
