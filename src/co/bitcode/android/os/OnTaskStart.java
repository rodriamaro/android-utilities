/*
 * Android Utilities
 * Copyright (C) 2010-2011  Lorenzo Villani
 *
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package co.bitcode.android.os;

/**
 * Interface used to allow the creator of an {@link ObservableTask} to run some code before the task
 * is started.
 * 
 * @since 0.0.1
 * @author Lorenzo Villani
 */
public interface OnTaskStart {
    /**
     * This method will be invoked before the task is started.
     * 
     * @param observableTask The task which is about to start.
     * @since 0.0.1
     */
    public void onTaskStart(ObservableTask<?, ?, ?> observableTask);
}
