import React, { Component } from "react";
import OverclockingNote from "./OverclockingNote";

class OverclockingNoteList extends Component {
  render() {
    const { overclockingNotes } = this.props;
    return (
      <div className="col-md-12 text-center">
        <h2>Overclocking Notes:</h2>
        <table class="table table-dark table-bordered">
          <thead>
            <tr>
              <th scope="col">Description</th>
            </tr>
          </thead>
          <tbody>
            {overclockingNotes.map(overclockingNote => {
              return (
                <OverclockingNote
                  key={overclockingNote.id}
                  overclockingNote={overclockingNote}
                />
              );
            })}
          </tbody>
        </table>
      </div>
    );
  }
}

export default OverclockingNoteList;
